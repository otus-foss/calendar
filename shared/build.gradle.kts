plugins {
    id("otus.kmp-app")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            api(libs.otus.designsystem)
        }
    }
}
