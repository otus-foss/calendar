# Motion

Calendar has no animation code of its own yet, and the welcome screen does not animate. Every
animation takes its duration and curve from Meta's `Motion` API: `MotionSpeed`, `MotionCurve`,
`motionTween(speed, curve)`, and the global speed multiplier `LocalMotionScale`. Meta's
[design system guide](https://github.com/reazndev/otus-meta/blob/main/docs/designsystem.md#motion)
explains the curves, the speeds, how durations are computed, and how to call it. The rules for
choosing a speed and a curve are in [AGENTS.md](../AGENTS.md#motion).

Meta's `tools/check_architecture.py` rejects `tween(`, `spring(`, and `CubicBezierEasing(` in
Calendar's production Kotlin, and `animateScrollToPage`, `animateScrollBy`, and `animate*AsState`
calls that leave their spec on the Compose default. `Motion.tween` and `motionTween` are allowed.
Reusable animation, such as the shared `AnimatedText` element, belongs in Meta.
