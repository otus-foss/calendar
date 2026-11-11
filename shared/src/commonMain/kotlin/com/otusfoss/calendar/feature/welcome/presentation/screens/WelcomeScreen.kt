package com.otusfoss.calendar.feature.welcome.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.otusfoss.core.designsystem.resources.Res
import com.otusfoss.core.designsystem.resources.symbol_calendar_month
import com.otusfoss.core.designsystem.shared.feedback.components.EmptyState

/** The placeholder Calendar shows until it has screens of its own. */
@Composable
fun WelcomeScreen() {
    Box(Modifier.safeDrawingPadding()) {
        EmptyState("Otus Calendar", "Your JMAP calendar app", Res.drawable.symbol_calendar_month)
    }
}
