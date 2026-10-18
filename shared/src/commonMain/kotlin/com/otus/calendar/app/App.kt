package com.otus.calendar.app

import androidx.compose.runtime.Composable
import com.otus.calendar.feature.welcome.presentation.screens.WelcomeScreen
import com.otus.designsystem.theme.OtusTheme

@Composable
fun App() {
    OtusTheme {
        WelcomeScreen()
    }
}
