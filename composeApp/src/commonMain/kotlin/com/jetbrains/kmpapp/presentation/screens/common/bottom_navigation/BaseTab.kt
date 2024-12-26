package com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation

import com.jetbrains.kmpapp.presentation.screens.common.toolbar.ToolbarState

abstract class BaseTab {
    var setToolbar: (ToolbarState) -> Unit = {}
    var onClearStack: () -> Unit = {}
}