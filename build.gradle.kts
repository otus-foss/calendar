plugins {
    id("otus.quality")
}

// CI runs each part of checkAll as its own parallel job.
tasks.register("checkTooling") {
    group = "verification"
    dependsOn("qualityCheck")
}

tasks.register("checkAndroidBuild") {
    group = "verification"
    dependsOn(":androidApp:lintDebug", ":androidApp:assembleDebug")
}

tasks.register("checkAll") {
    group = "verification"
    description = "Run portable quality checks, Android lint, and debug compilation."
    dependsOn("checkTooling", "checkAndroidBuild")
}
