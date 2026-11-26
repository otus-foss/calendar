plugins {
    id("otus.android-app")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.androidx.activity.compose)
}

android {
    defaultConfig {
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
        // Its own app id, so a debug build installs beside the stable app.
        getByName("debug") {
            applicationIdSuffix = ".dev"
        }
    }
}
