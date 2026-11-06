plugins {
    id("otus.kmp-app")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material)
            implementation(libs.compose.ui)
            implementation(libs.compose.resources)
            api(libs.otus.designsystem)
        }
        commonTest.dependencies { implementation(kotlin("test")) }
    }
}
