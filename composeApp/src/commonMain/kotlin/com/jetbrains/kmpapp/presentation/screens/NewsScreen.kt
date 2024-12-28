package com.jetbrains.kmpapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.jetbrains.kmpapp.presentation.AppViewModel
import com.jetbrains.kmpapp.presentation.screens.common.ScreenState
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.news
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


class NewsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalTabNavigator.current
        val appViewModel = koinViewModel<AppViewModel>()
        appViewModel.setScreenState(
            ScreenState(
                title = stringResource(Res.string.news),
                isBackArrowEnable = false
            )
        )

        Box(modifier = Modifier.fillMaxSize()) {

        }
    }
}