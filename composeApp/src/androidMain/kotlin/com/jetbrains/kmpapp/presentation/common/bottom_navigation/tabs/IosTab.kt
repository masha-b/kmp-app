package com.jetbrains.kmpapp.presentation.common.bottom_navigation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.jetbrains.kmpapp.R
import com.jetbrains.kmpapp.domain.models.apps.VkpAppType
import com.jetbrains.kmpapp.presentation.screens.apps.AppsScreen

object IosTab : Tab {

    var onClearStack: (() -> Unit)? = null
        private set

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.ios)
            val icon = painterResource(R.drawable.ic_apple_logo)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val nav = LocalNavigator.current
        Navigator(AppsScreen(VkpAppType.IOS)) { navigator ->
            println("555555555 ios nav $navigator $nav")
            onClearStack = { nav?.popAll() }
            CurrentScreen()
        }
    }
}