package com.jetbrains.kmpapp

import androidx.compose.runtime.Composable
import platform.UIKit.UIApplication

@Composable
actual fun closeApp() {
    UIApplication.sharedApplication.performSelector("suspend")
}

