# Otus Calendar

Development, pull requests, and issues use
[otus-calendar on GitHub](https://github.com/reazndev/otus-calendar). See [hosting and CI](docs/hosting.md).

Kotlin Multiplatform + Compose app for Android and iOS, intended as the calendar client in the Otus
JMAP suite. This initial scaffold includes a welcome screen; JMAP discovery, authentication,
synchronization and product features are not implemented yet.

## Structure

- `shared`: common Kotlin code and Compose UI, with platform integrations.
- `androidApp`: Android application entry point.
- `iosApp`: Xcode project hosting the shared Compose UI.

Application and bundle ID: `com.otus.calendar` (verify ownership before publishing).

## Android

Android is the primary platform; iOS remains a secondary target.

Use JDK 21 and Android Studio with the Kotlin Multiplatform plugin. Install Android SDK 37 and set
`ANDROID_HOME` or add `sdk.dir=/your/android/sdk` to untracked `local.properties`. Open this
directory in Android Studio and run `androidApp`.

The build downloads shared Otus libraries from GitHub Packages, which needs a GitHub token. See
[Shared libraries](docs/shared-libraries.md) for the one-time setup.

```sh
./gradlew :androidApp:assembleDebug
```

## iOS

On macOS, install Xcode and JDK 21. Open `iosApp/iosApp.xcodeproj`, select an iPhone simulator and
run. For a physical device, set `TEAM_ID` in `iosApp/Configuration/Config.xcconfig` and configure
signing. Xcode builds the shared Kotlin framework automatically. Targets: iOS ARM64 devices and
Apple Silicon simulators, iOS 15.3+.

## Toolchain and attribution

Versions are pinned in `gradle/libs.versions.toml`. The Gradle wrapper includes a distribution
checksum. Derived from [Kotlin/KMP-App-Template](https://github.com/Kotlin/KMP-App-Template) at
commit `6b6d09e4f3f5e845116422d59b658791ed6149c6`; its Apache 2.0 license is retained in `LICENSE`.
Sample museum code and unrelated platform targets were removed.

## Engineering policy

See [shared libraries](docs/shared-libraries.md) for the otus-meta libraries Calendar uses: the
design system, date formatting, avatars, and the settings pages and state. See
[date formatting](docs/date-formatting.md) for how Calendar wires saved format choices into future
settings and screens.

Read [AGENTS.md](AGENTS.md), [architecture](docs/architecture.md), and
[quality checks](docs/quality.md). UI follows elements → components → screens. Install the tools in the quality guide and
run `./gradlew checkAll` before submitting changes. Use `./gradlew formatAll` to format.

See [attendee avatars](docs/avatars.md) for how Calendar uses Meta's avatar service and the Public
Suffix List it bundles.

## Commit messages

Use `type(scope): short action`, a blank line, and 1–8 bullets explaining the changes:

```text
feat(inbox): add avatars

- Show contact photos beside inbox senders.
- Fall back to initials when a photo is unavailable.
```

Use lowercase types and scopes, start the subject with an imperative verb, and aim for a title
of at most 72 characters without a final period. Apply the unslop skill to the entire message
when available. Keep the wording plain and describe only changes in the commit.

Enable the versioned `commit-msg` hook once per checkout, including new clones. Run this from
the repository root with Python 3.11+ installed:

```sh
git config --local core.hooksPath .githooks
```

The hook rejects invalid title syntax, missing separators, and bodies outside the 1–8 bullet
limit. Review wording and the title-length target manually. Git-generated merge messages and
temporary autosquash messages are exempt; final squash messages follow the rules. CI tests the
checker but does not inspect commit history or install local hooks.
See [the full commit rules](AGENTS.md#commit-messages) and [hook details](docs/quality.md#commit-message-review).
