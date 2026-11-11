rootProject.name = "calendar"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// Calendar builds core from source: the sibling checkout ../core, or the checkout that the
// otus.core.path Gradle property names. See docs/shared-libraries.md.
pluginManagement {
    val otusCore = file(providers.gradleProperty("otus.core.path").getOrElse("../core"))
    require(otusCore.resolve("build-logic").isDirectory) {
        "Calendar builds core from source. Clone it to $otusCore or set otus.core.path."
    }
    // Gradle resolves the convention plugins' own dependencies from these repositories.
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild(otusCore.resolve("build-logic").path)
}

plugins {
    id("otus.settings")
}

include(":shared")
include(":androidApp")
