# Otus Calendar developer guide

Development, pull requests, and issues use
[calendar on GitHub](https://github.com/otus-foss/calendar). See [hosting and CI](hosting.md).

Kotlin Multiplatform + Compose app for Android and iOS, intended as the calendar client in the Otus
JMAP suite. This initial scaffold includes a welcome screen; JMAP discovery, authentication,
synchronization and product features are not implemented yet.

## Structure

- `shared`: common Kotlin code and Compose UI, with platform integrations.
- `androidApp`: Android application entry point.
- `iosApp`: Xcode project hosting the shared Compose UI.

Application and bundle ID: `com.otusfoss.calendar` (verify ownership before publishing).

## Android

Android is the primary platform; iOS remains a secondary target.

Use JDK 21 and Android Studio with the Kotlin Multiplatform plugin. Install Android SDK 37 and set
`ANDROID_HOME` or add `sdk.dir=/your/android/sdk` to untracked `local.properties`. Open this
directory in Android Studio and run `androidApp`.

The build compiles the shared Otus libraries from an core checkout next to this one,
`../core`, or from the checkout the `otus.core.path` Gradle property names. See
[Shared libraries](shared-libraries.md).

```sh
./gradlew :androidApp:assembleDebug
```

## iOS

On macOS, install Xcode and JDK 21. Open `iosApp/iosApp.xcodeproj`, select an iPhone simulator and
run. For a physical device, set `TEAM_ID` in `iosApp/Configuration/Config.xcconfig` and configure
signing. Xcode builds the shared Kotlin framework automatically. Targets: iOS ARM64 devices and
Apple Silicon simulators, iOS 15.3+.

## Toolchain and attribution

Versions are pinned in core's `gradle/libs.versions.toml`, the suite catalog. The Gradle
wrapper includes a distribution checksum. Derived from [Kotlin/KMP-App-Template](https://github.com/Kotlin/KMP-App-Template) at
commit `6b6d09e4f3f5e845116422d59b658791ed6149c6`; its Apache 2.0 license is retained in `LICENSES/Apache-2.0-KMP-App-Template.txt`.
Sample museum code and unrelated platform targets were removed.

## Engineering policy

See [shared libraries](shared-libraries.md) for the core libraries Calendar uses, so
far only the design system.

Read [AGENTS.md](../AGENTS.md), [architecture](architecture.md), and
[quality checks](quality.md). UI follows elements → components → screens. Install the tools in the quality guide and
run `./gradlew checkAll` before submitting changes. Use `./gradlew formatAll` to format.

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
temporary autosquash messages are exempt; final squash messages follow the rules. The hook runs
core's checker, which core's CI tests; nothing inspects commit history or installs local
hooks. See [the full commit rules](../AGENTS.md#commit-messages) and
[hook details](https://github.com/otus-foss/core/blob/main/docs/quality.md#commit-messages).
