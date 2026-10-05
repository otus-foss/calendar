package com.otus.calendar.designsystem.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.otus.calendar.designsystem.theme.AvatarTokens
import com.otus.calendar.designsystem.theme.OtusColors

/** Takes resolved display values only. Calendar screens resolve attendees through AvatarService. */
@Composable
fun Avatar(
    initial: String,
    modifier: Modifier = Modifier,
    image: ImageBitmap? = null,
    colorIndex: Int = 0,
) {
    val background =
        when (colorIndex) {
            1 -> OtusColors.bgLight
            2 -> OtusColors.bgDark
            else -> OtusColors.accentDark
        }
    Box(
        modifier.size(AvatarTokens.size).clip(CircleShape).background(background),
        contentAlignment = Alignment.Center,
    ) {
        if (image == null) {
            Text(initial, fontSize = AvatarTokens.textSize, color = OtusColors.text1)
        } else {
            Image(
                image,
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
            )
        }
    }
}
