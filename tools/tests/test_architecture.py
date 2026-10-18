"""Regression tests showing representative legal/illegal dependency directions."""

import io
import sys
import tempfile
import unittest
from contextlib import redirect_stderr, redirect_stdout
from pathlib import Path
from unittest import mock

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import check_architecture
from check_architecture import drawable_violations, violations


class ArchitectureTests(unittest.TestCase):
    def check(self, package, body, filename="Example.kt"):
        path = (
            Path("shared/src/commonMain/kotlin/com/otus/mail")
            / package.replace(".", "/")
            / filename
        )
        return violations(path, f"package com.otus.mail.{package}\n{body}", "com.otus.mail")

    def test_elements_cannot_import_components(self):
        self.assertTrue(
            self.check("designsystem.elements", "import com.otus.mail.designsystem.components.Card")
        )

    def test_fully_qualified_reference_is_checked(self):
        self.assertTrue(
            self.check(
                "designsystem.elements", "fun x() = com.otus.mail.designsystem.components.Card()"
            )
        )

    def test_component_cannot_import_screen_using_alias(self):
        self.assertTrue(
            self.check(
                "feature.inbox.presentation.components",
                "import com.otus.mail.feature.inbox.presentation.screens.InboxScreen as View",
            )
        )

    def test_domain_cannot_import_data(self):
        self.assertTrue(
            self.check("feature.inbox.domain", "import com.otus.mail.feature.inbox.data.Messages")
        )

    def test_domain_cannot_access_network(self):
        self.assertTrue(self.check("feature.inbox.domain", "import io.ktor.client.HttpClient"))

    def test_presentation_cannot_import_data(self):
        self.assertTrue(
            self.check(
                "feature.inbox.presentation", "import com.otus.mail.feature.inbox.data.Messages"
            )
        )

    def test_feature_cannot_import_sibling(self):
        self.assertTrue(
            self.check(
                "feature.inbox.domain", "import com.otus.mail.feature.contacts.domain.Person"
            )
        )

    def test_component_cannot_hold_viewmodel(self):
        self.assertTrue(
            self.check("feature.inbox.presentation.components", "fun row(vm: InboxViewModel) {}")
        )

    def test_common_cannot_import_android(self):
        self.assertTrue(self.check("core.platform", "import android.content.Context"))

    def test_allowed_screen_and_domain_dependency(self):
        self.assertFalse(
            self.check(
                "feature.inbox.presentation.screens",
                "import com.otus.mail.feature.inbox.domain.Message\n"
                "@Composable fun InboxScreen() {}",
                "InboxScreen.kt",
            )
        )

    def test_comments_and_strings_do_not_create_dependencies(self):
        self.assertFalse(
            self.check(
                "core.jmap",
                "// import com.otus.mail.feature.inbox.data.Messages\n"
                'val example = "com.otus.mail.feature.inbox.data.Messages"',
            )
        )

    def test_package_must_match_path(self):
        path = Path("shared/src/commonMain/kotlin/com/otus/mail/core/Example.kt")
        self.assertTrue(violations(path, "package com.otus.mail.app", "com.otus.mail"))

    def test_color_literals_belong_in_meta(self):
        error = "literal colors belong in otus-meta designsystem/theme"
        for package in (
            "designsystem.elements",
            "designsystem.theme",
            "feature.inbox.presentation",
        ):
            with self.subTest(package=package):
                self.assertIn(error, self.check(package, "val color = Color(0xFF202021)"))

    def test_drawables_are_rejected_in_every_source_set(self):
        with tempfile.TemporaryDirectory() as folder:
            root = Path(folder)
            for name in (
                "shared/src/commonMain/composeResources/drawable/a.xml",
                "shared/src/androidMain/composeResources/drawable-night/b.png",
                "androidApp/src/main/res/drawable-v24/c.xml",
                "androidApp/src/main/res/values/colors.xml",
            ):
                (root / name).parent.mkdir(parents=True, exist_ok=True)
                (root / name).write_text("<x />")
            errors = drawable_violations(root)
        self.assertEqual(3, len(errors), errors)
        self.assertFalse(any("values" in error for error in errors))

    def test_central_ui_rejects_app_tokens_and_elements(self):
        for layer in ("elements", "components", "theme"):
            path = Path(
                f"shared/src/commonMain/kotlin/com/otus/mail/designsystem/{layer}/Example.kt"
            )
            errors = violations(
                path,
                f"package com.otus.mail.designsystem.{layer}",
                "com.otus.mail",
                central_ui=True,
            )
            self.assertIn("visual elements, components, and tokens belong in otus-meta", errors)

    def test_central_ui_allows_feature_adapters_using_meta(self):
        path = Path(
            "shared/src/commonMain/kotlin/com/otus/mail/feature/inbox/presentation/components/Example.kt"
        )
        self.assertFalse(
            violations(
                path,
                "package com.otus.mail.feature.inbox.presentation.components\n"
                "import com.otus.designsystem.components.ContentRow",
                "com.otus.mail",
                central_ui=True,
            )
        )

    def check_calendar(self, files):
        """Run the checker over a temporary Calendar checkout holding only [files]."""
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            (root / "settings.gradle.kts").write_text('rootProject.name = "otus-calendar"\n')
            for name, text in files.items():
                (root / name).parent.mkdir(parents=True, exist_ok=True)
                (root / name).write_text(text)
            errors = io.StringIO()
            with (
                mock.patch.object(check_architecture, "ROOT", root),
                redirect_stderr(errors),
                redirect_stdout(io.StringIO()),
            ):
                return check_architecture.main(), errors.getvalue()

    def test_calendar_rejects_local_copies_of_meta_design_system(self):
        theme = "shared/src/commonMain/kotlin/com/otus/calendar/designsystem/theme"
        status, errors = self.check_calendar(
            {
                f"{theme}/MotionTokens.kt": (
                    "package com.otus.calendar.designsystem.theme\nobject MotionTokens\n"
                ),
            }
        )
        self.assertEqual(1, status)
        self.assertIn("visual elements, components, and tokens belong in otus-meta", errors)

    def test_calendar_app_wiring_may_use_meta_theme(self):
        status, errors = self.check_calendar(
            {
                "shared/src/commonMain/kotlin/com/otus/calendar/app/App.kt": (
                    "package com.otus.calendar.app\n"
                    "import com.otus.designsystem.theme.OtusTheme\n"
                    "@Composable fun App() { OtusTheme {} }\n"
                ),
            }
        )
        self.assertEqual(0, status, errors)

    def test_suppression_is_rejected(self):
        self.assertTrue(self.check("core.jmap", '@Suppress("ALL")\nfun x() {}'))


if __name__ == "__main__":
    unittest.main()
