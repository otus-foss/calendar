# Quality gate

Meta's [quality guide](https://github.com/reazndev/otus-meta/blob/main/docs/quality.md) describes the commands, the convention plugins, the
shared tools and rules, file-length exceptions, commit messages, and coverage for every Otus
repository. Calendar runs them from its otus-meta checkout; see
[shared libraries](shared-libraries.md). This guide covers what only Calendar adds.

## Commands

Requires JDK 21, Python 3.11+, the Android SDK, and an otus-meta checkout at `../otus-meta` or at
`otus.meta.path`. One-time setup:

```sh
python3 -m venv .venv
.venv/bin/python -m pip install -r requirements-quality.txt
git config --local core.hooksPath .githooks
```

During development, batch edits and run the linters:

```sh
./gradlew spotlessCheck detekt ruffCheck ruffFormatCheck
```

| Command | Scope |
| --- | --- |
| `checkTooling` | `qualityCheck` |
| `checkAndroidBuild` | Android lint and debug assembly |
| `checkAll` | both |

Calendar has no tests yet, so it has no `checkTests` part and no coverage workflow. The first test
brings back the `checkTests` task, its two CI shards, and `coverage.yml` as Mail has them.

Calendar has no Python tools of its own, so it has no `architectureTest`, and Ruff finds no files
to check until it gets some.

## iOS

Swift formatting and lint and the full iOS app build need macOS; they are not part of `formatAll`
or `checkAll`. On macOS install `nicklockwood/SwiftFormat@0.63.1` and `realm/SwiftLint@0.65.1`
via Mint, then run:

```sh
mint run swiftformat iosApp
mint run swiftformat --lint iosApp
mint run swiftlint lint --strict
```

SwiftFormat uses `--commas inline` to match SwiftLint's trailing-comma rule. SwiftLint's
`file_length` rule is off, because Meta's architecture check enforces the Swift limit.

Shared Kotlin iOS source compilation can also run on Linux with
`./gradlew :shared:compileKotlinIosSimulatorArm64 :shared:compileKotlinIosArm64`.

## CI

The `quality` workflow's `check` job runs `checkAll` as two parallel jobs: `checkTooling` and
`checkAndroidBuild`. The `ios`
job runs Swift formatting and lint plus a simulator build on macOS 15 with Xcode 26.3. See
[hosting and CI](hosting.md).
