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
            api(libs.otus.avatar)
            api(libs.otus.dateformat)
            api(libs.otus.designsystem)
            api(libs.otus.settings)
        }
        // Platform engines for the credential-free HttpAvatarImageLoader in com.otus:avatar.
        androidMain.dependencies { implementation(libs.ktor.client.okhttp) }
        iosMain.dependencies { implementation(libs.ktor.client.darwin) }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.ktor.client.mock)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
