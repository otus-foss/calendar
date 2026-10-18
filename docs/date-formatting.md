# Date formatting

Calendar uses the `com.otus:dateformat` library from otus-meta for date display and preference
storage. Its choices, platform behavior, and API are documented in
[otus-meta's date-formatting guide](https://github.com/reazndev/otus-meta/blob/main/docs/dateformat.md).
`DateFormatSettingsState` and the shared settings pages come from `com.otus:settings`.

## Settings state

`DateFormatSettingsState` is a presentation adapter around `DateFormatPreferenceStore`. Calendar
has no Settings route yet. When it adds one, the route hosts Meta's
`com.otus.settings.presentation.screens.AppearanceSettingsScreen(dateFormat = state, onBack = ...)`.
That page already shows the current format and opens a `ChoiceSheet` of
`DateFormatPreference.entries`, each with its `patternLabel` and a sample date, and it calls
`state.select(choice)`; persistence stays outside the selection controls. Calendar-only sections go
in the screen's `content` slot, and the top Settings page uses Meta's `SettingsScreen` with
Calendar's `SettingsEntry` rows. Do not build local copies of these pages: changes to them,
including translated labels, belong in Meta.

At the platform entry point, construct the state with `AndroidDateFormatPreferenceStore(context)`
or `IosDateFormatPreferenceStore()`. App wiring uses `rememberDateFormatter(state)` and passes the
formatter to screens. Changing `state.preference` through `select` updates the composition. Preview
and test compositions can use a state without a store. Each installed app keeps its own
preference; Mail and Calendar do not share it.

## Integration and validation

Calendar has the `rememberDateFormatter` wiring helper. Its entry points do not construct a
preference store yet, and the welcome screen shows no dates. Future calendar screens should receive
the formatter from app wiring when those screens are implemented.

The formatter, storage, and settings-state tests run in otus-meta, including the Android locale and
storage tests. The iOS adapters need the macOS build and runtime validation.
