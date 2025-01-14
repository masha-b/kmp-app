package com.jetbrains.kmpapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import com.jetbrains.kmpapp.presentation.AppViewModel
import com.jetbrains.kmpapp.presentation.common.ScreenState
import org.koin.compose.viewmodel.koinViewModel


class ProfileScreen : Screen {

    @Composable
    override fun Content() {

        val appViewModel = koinViewModel<AppViewModel>()
        appViewModel.setScreenState(ScreenState(isEnable = false))

        Box(modifier = Modifier.fillMaxSize().background(color = Color.Green)) {

        }
    }
}