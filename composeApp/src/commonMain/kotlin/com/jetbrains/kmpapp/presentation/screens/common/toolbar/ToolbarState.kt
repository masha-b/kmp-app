package com.jetbrains.kmpapp.presentation.screens.common.toolbar

data class ToolbarState(
    val title: String = "",
    val isEnable: Boolean = true,
    val isBackArrowEnable: Boolean = false,
//    val onLoginsNavigationClick: (() -> Unit)? = null,
//    val onAddLoginClick: (() -> Unit)? = null,
//    val onSearchClick: (() -> Unit)? = null,
//    val onLogoutClick: (() -> Unit)? = null
    val onBackPressed: (() -> Unit)? = null
)