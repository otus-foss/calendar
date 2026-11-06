# Shared libraries

Code that Calendar and Mail both use lives in
[otus-meta](https://github.com/reazndev/otus-meta). Calendar uses `com.otus:designsystem`
(`libs.otus.designsystem`) for `OtusTheme`.

Add another library when a Calendar screen first needs it, not before. Meta's `dateformat` and
`settings` give a future Settings route the shared date-format choice and the `SettingsScreen` and
`AppearanceSettingsScreen` pages; `avatar` resolves attendee avatars. Do not copy those pages
into Calendar: their changes belong in Meta.

## Building against Meta

Calendar builds otus-meta from source: the checkout next to it, `../otus-meta`, or the one the
`otus.meta.path` Gradle property names, for example in `~/.gradle/gradle.properties` or with
`-Potus.meta.path=../meta-worktree`. Gradle replaces each `com.otus:<module>` with the matching
project of that checkout, and the build also takes Meta's convention plugins, its version catalog
as `libs`, its tools, and its detekt configuration from it. Nothing is downloaded from GitHub
Packages and no Meta version is pinned. See
[otus-meta's consuming guide](https://github.com/reazndev/otus-meta/blob/main/docs/consuming.md).

Calendar uses no library that the other repositories do not, so it has no catalog of its own.

## Changing shared code

1. Change the library in otus-meta on a branch, and make the matching Calendar change on a branch
   with the same name.
2. Build Calendar against that Meta branch locally. CI does the same: it checks out the Meta branch
   with this branch's name when there is one, else Meta's `main`, and the `pull request`
   workflow labels the pull request `needs meta`.
3. Merge the Meta pull request first, then Calendar's, so Calendar's `main` always builds against
   Meta's `main`.

Meta is the central owner of visual elements, reusable components, tokens, and symbols. Add new
ones there, even when Calendar is their only consumer. Calendar keeps feature screens and the adapters
that map calendar data into shared renderers.
