# Shared libraries

Code that Calendar and Mail both use lives in
[otus-core](https://github.com/otus-foss/otus-core). Calendar uses `com.otusfoss.core:designsystem`
(`libs.otus.designsystem`) for `OtusTheme`.

Add another library when a Calendar screen first needs it, not before. Core's `dateformat` and
`settings` give a future Settings route the shared date-format choice and the `SettingsScreen` and
`AppearanceSettingsScreen` pages; `avatar` resolves attendee avatars. Do not copy those pages
into Calendar: their changes belong in Core.

## Building against Core

Calendar builds otus-core from source: the checkout next to it, `../otus-core`, or the one the
`otus.core.path` Gradle property names, for example in `~/.gradle/gradle.properties` or with
`-Potus.core.path=../core-worktree`. Gradle replaces each `com.otusfoss.core:<module>` with the matching
project of that checkout, and the build also takes Core's convention plugins, its version catalog
as `libs`, its tools, and its detekt configuration from it. Nothing is downloaded from GitHub
Packages and no Core version is pinned. See
[otus-core's consuming guide](https://github.com/otus-foss/otus-core/blob/main/docs/consuming.md).

Calendar uses no library that the other repositories do not, so it has no catalog of its own.

## Changing shared code

1. Change the library in otus-core on a branch, and make the matching Calendar change on a branch
   with the same name.
2. Build Calendar against that Core branch locally. CI does the same: it checks out the Core branch
   with this branch's name when there is one, else Core's `main`, and the `pull request`
   workflow labels the pull request `needs core`.
3. Merge the Core pull request first, then Calendar's, so Calendar's `main` always builds against
   Core's `main`.

Core is the central owner of visual elements, reusable components, tokens, and symbols. Add new
ones there, even when Calendar is their only consumer. Calendar keeps feature screens and the adapters
that map calendar data into shared renderers.
