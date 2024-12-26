package com.jetbrains.kmpapp.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.kmpapp.presentation.screens.common.EmptyScreenContent
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs.SettingsTab
import com.jetbrains.kmpapp.presentation.screens.common.toolbar.ToolbarState
import com.jetbrains.kmpapp.presentation.screens.detail.DetailScreen


class SettingsScreen(val setToolbar: (ToolbarState) -> Unit,) : Screen {

    @Composable
    override fun Content() {


        setToolbar.invoke(ToolbarState("Настройки"))

        Box(modifier = Modifier.fillMaxSize().background(color = Color.Blue)) {

        }
    }
}