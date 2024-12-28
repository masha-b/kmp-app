package com.jetbrains.kmpapp.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.internal.BackHandler
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.jetbrains.kmpapp.CloseApp
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.TabNavigationItem
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs.AuthTab
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs.HomeTab
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs.SettingsTab
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs.ProfileTab
import com.jetbrains.kmpapp.presentation.screens.common.toolbar.Toolbar
import com.jetbrains.kmpapp.presentation.screens.common.toolbar.ToolbarState


@OptIn(InternalVoyagerApi::class)
@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        var isNeedCloseApp: Boolean by remember { mutableStateOf(false) }
        var toolbarState: ToolbarState by remember { mutableStateOf(ToolbarState()) }
        fun setToolbarState(state: ToolbarState) { toolbarState = state }
        val homeTab = HomeTab
        val settingsTab = SettingsTab
        val profileTab = ProfileTab
        val authTab = AuthTab

        TabNavigator(homeTab) { navigator ->

            if (isNeedCloseApp) CloseApp()

            BackHandler(enabled = true) {
                if (navigator.current.key == HomeTab.key) {
                    isNeedCloseApp = true
                } else
                    navigator.current = homeTab
            }

            Scaffold(
                topBar = {
                    with(toolbarState) {
                        if (isEnable) {
                            Toolbar(
                                title = title,
                                isBackVisible = isBackArrowEnable,
                                onBackNavigation = { onBackPressed?.invoke() }
                            )
                        }
                    }
                },
                content = { CurrentTab() },
                bottomBar = {
                    BottomNavigation {
                        TabNavigationItem(homeTab)
                        TabNavigationItem(settingsTab)
                        TabNavigationItem(profileTab)
                        TabNavigationItem(authTab)
                    }
                }
            )
        }
    }
}