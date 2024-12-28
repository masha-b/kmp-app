package com.jetbrains.kmpapp.presentation.screens.common.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import com.jetbrains.kmpapp.presentation.screens.common.utils.NoRippleInteractionSource

@Composable
fun Progress() {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(
            indication = null,
            interactionSource = NoRippleInteractionSource()
        ) {}
    ) {
        LocalFocusManager.current.clearFocus()
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color.Blue)
    }
}