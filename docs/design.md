# Color theme

Calendar draws with `OtusColors` and `OtusTheme` from Meta's `com.otusfoss.core:designsystem`. It has no
Theme page yet, so it shows the default theme, Otus with Otus blue, whose palette Meta's
[mail design foundations](https://github.com/reazndev/otus-meta/blob/main/docs/suite/mail-design.md)
records. System light/dark settings do not change the app theme. Ordinary text inherits `text1`
at full opacity; muted text requires an explicit `text2` or `text3` choice. See Meta's
[decision 0014](https://github.com/reazndev/otus-meta/blob/main/docs/decisions/0014-user-themes.md)
for the themes the user will pick from.

Error UI must communicate meaning through text, not color alone.

Android's starting window uses the default theme's background, text, and accent tokens from
`androidApp/src/main/res/values/colors.xml`, which Meta's `tools/launch_colors.py` writes; the
architecture check fails when it drifts. System bar icons use their light appearance. The iOS host
requests a dark appearance too.
