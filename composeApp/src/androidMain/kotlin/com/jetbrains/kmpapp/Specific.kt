package com.jetbrains.kmpapp

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun CloseApp() {
    val context = LocalContext.current
    if (context is Activity) {
        context.finishAffinity()
    }
}