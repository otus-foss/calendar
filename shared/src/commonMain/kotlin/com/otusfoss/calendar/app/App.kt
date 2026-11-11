package com.otusfoss.calendar.app

import androidx.compose.runtime.Composable
import com.otusfoss.calendar.feature.welcome.presentation.screens.WelcomeScreen
import com.otusfoss.core.designsystem.theme.OtusTheme

@Composable
fun App() {
    OtusTheme {
        WelcomeScreen()
    }
}
