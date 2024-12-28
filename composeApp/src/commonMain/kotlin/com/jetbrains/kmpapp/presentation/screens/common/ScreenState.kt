package com.jetbrains.kmpapp.presentation.screens.common

import androidx.compose.ui.graphics.vector.ImageVector

data class ScreenState(
    val title: String = "",
    val isEnable: Boolean = true,
    val isBackArrowEnable: Boolean = true,
    val onBackPressed: (() -> Unit)? = null,
    val actionButtons: List<ActionButton> = listOf(),
    val isBottomNavigationEnable: Boolean = true,
    val isLoading: Boolean = false,
    val onRefresh: (() -> Unit)? = null,
    val error: String? = null,
    val onErrorHandled: (() -> Unit)? = null,
)

data class ActionButton(
    val key: String,
    val icon: ImageVector,
    val isEnable: Boolean = true,
    val onClick: () -> Unit
)