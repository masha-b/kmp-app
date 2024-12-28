package com.jetbrains.kmpapp

import androidx.compose.runtime.Composable
import platform.UIKit.UIApplication

@Composable
actual fun CloseApp() {
    UIApplication.sharedApplication.performSelector("suspend")
}

