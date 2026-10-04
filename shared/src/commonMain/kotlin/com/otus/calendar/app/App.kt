package com.otus.calendar.app

import androidx.compose.runtime.Composable
import com.otus.calendar.designsystem.theme.OtusTheme
import com.otus.calendar.feature.welcome.presentation.screens.WelcomeScreen

@Composable
fun App() {
    OtusTheme {
        WelcomeScreen()
    }
}
