package com.jetbrains.kmpapp.platform

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

@OptIn(ExperimentalNativeApi::class)
actual val isDebug = Platform.isDebugBinary