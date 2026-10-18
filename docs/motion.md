# Motion

Calendar has no animation code of its own. Motion comes from Meta's `com.otus:designsystem`
library, which Calendar pins at 0.7.0; see [shared libraries](shared-libraries.md). The welcome
screen does not animate.

In 0.7.0, `theme/MotionTokens` holds the suite's `standard`, `enter`, and `exit` curves and the
text replacement durations. `elements/AnimatedText` replaces a label with a clipped vertical slide
and fade: increasing order slides upward, decreasing order slides downward, and unchanged text
stays still. Compose's motion duration scale applies. Future Calendar screens can use
`AnimatedText` from the pinned release.

Meta's `main` replaces `MotionTokens` with the `Motion` API: `MotionSpeed`, `MotionCurve`,
`motionTween(speed, curve)`, and the global speed multiplier `LocalMotionScale`. Its
[design system guide](https://github.com/reazndev/otus-meta/blob/main/docs/designsystem.md#motion)
explains the speeds and curves. Calendar adopts that API when it pins a Meta release that includes
it. Until then, Calendar adds no raw duration constant, `tween` with a literal, new
`CubicBezierEasing`, or animation with a Compose default spec; reusable animation belongs in Meta.

Mail's `AnimatedTextTest` covers the shared element's directions, interruption, and unchanged
text. Physical-device motion review and iOS runtime validation remain open.
