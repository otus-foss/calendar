# Shared libraries

Code that Calendar and Mail both use lives in
[otus-meta](https://github.com/reazndev/otus-meta) and arrives here as a pinned release. Calendar
uses:

| Library | Version catalog entry |
| --- | --- |
| `com.otus:avatar` | `libs.otus.avatar` |
| `com.otus:dateformat` | `libs.otus.dateformat` |
| `com.otus:designsystem` | `libs.otus.designsystem` |
| `com.otus:settings` | `libs.otus.settings` |

Calendar pins Meta 0.7.0, the release Mail uses, for all four libraries. `com.otus:avatar` also
brings `com.otus:jmap` as an API dependency. All otus-meta libraries share one version,
`otus-meta` in `gradle/libs.versions.toml`. Local source substitution is optional development
configuration; normal builds download the pinned GitHub Packages artifacts.

These libraries replace Calendar's earlier copies of `core/avatar`, `core/dateformat`, the
design-system theme and elements, and `DateFormatSettingsState`. Calendar keeps its app wiring
and screens, and bundles the Public Suffix List for the avatar resolver it will create; see
[avatars](avatars.md).

`com.otus:settings` holds the shared settings pages, `SettingsScreen` and
`AppearanceSettingsScreen`, as well as `DateFormatSettingsState`. Calendar uses only the state
today. A future Calendar Settings route hosts those pages and adds Calendar-only sections through
the appearance page's `content` slot; see [date formatting](date-formatting.md#settings-state).

## Token setup

The release registry is `https://maven.pkg.github.com/reazndev/otus-meta`. `settings.gradle.kts`
declares it as exclusive content for the `com.otus` group, so Gradle never resolves a `com.otus`
artifact from Maven Central or Google. GitHub Packages needs a token for every download. Create a
classic personal access token with the `read:packages` scope.

On a development machine, add credentials to `~/.gradle/gradle.properties`, never to this repository:

```properties
otusMetaUsername=<GitHub username>
otusMetaPassword=<GitHub token with read:packages>
```

In Calendar's GitHub Actions settings, store the token as the `OTUS_META_PACKAGES_TOKEN` secret.
SSH Git credentials do not authenticate Maven downloads.

## Package status

Meta's tag workflow published 0.7.0 with every module's Android, iOS device, and iOS simulator
variants to GitHub Packages. Tag publishing has since been paused on Meta's `main`; see
[otus-meta's releasing guide](https://github.com/reazndev/otus-meta/blob/main/docs/releasing.md).
Versions 0.3.1 to 0.5.0 are not on GitHub Packages. iOS linking against the artifacts is
unverified while the macOS `ios` job is paused. See [hosting and CI](hosting.md).

## Changing shared code

1. Change the library in otus-meta.
2. Try it here with `./gradlew checkAll -Potus.meta.path=../otus-meta`. Gradle then builds the
   library from that checkout instead of downloading it.
3. Release the library from otus-meta.
4. Update `otus-meta` in `gradle/libs.versions.toml` and run `./gradlew checkAll` without the
   property.

`main` must always build from a released version. See
[otus-meta's consuming guide](https://github.com/reazndev/otus-meta/blob/main/docs/consuming.md)
for details.

Meta is the central owner of visual elements, reusable components, tokens, and symbols. Add new
ones there, even when Calendar is their only consumer. Calendar keeps feature screens and the
adapters that map calendar data into shared renderers.

## Checking a release candidate without source substitution

Publish Meta into a temporary Maven repository from its own checkout:

```sh
./gradlew publishToMavenLocal -Dmaven.repo.local=/tmp/otus-meta-candidate
```

Then run Calendar against those artifacts from this checkout:

```sh
./gradlew checkAll -Potus.meta.repository=file:///tmp/otus-meta-candidate
./gradlew :shared:compileKotlinIosSimulatorArm64 :shared:compileKotlinIosArm64 -Potus.meta.repository=file:///tmp/otus-meta-candidate
```

`otus.meta.repository` is an explicit development override. It resolves only `com.otus` from
that repository and disables composite substitution, even if `otus.meta.path` is set in your
machine's Gradle properties. Other dependencies keep their existing repositories. No remote
package credentials are required for a file repository. The pinned version still applies.

This checks artifact metadata, transitive dependencies, compilation, and Android resource
packaging independently of a sibling source build. Kotlin iOS compilation on Linux does not
verify Xcode linking or simulator resource loading. After the remote release, repeat the build
without either override to verify GitHub Packages access and the released artifacts.
