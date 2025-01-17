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
import com.jetbrains.kmpapp.presentation.screens.auth.AuthScreen

object AndroidTab : Tab {

    var onClearStack: (() -> Unit)? = null
        private set

    var openAuthScreen: (() -> Unit)? = null
        private set

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.android)
            val icon = painterResource(R.drawable.ic_android_logo)

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
        Navigator(AuthScreen()) { navigator ->
            println("555555555 android nav $navigator $nav")
            onClearStack = { navigator.popAll() }
            openAuthScreen = {
                println("555555555 openAuth")
                navigator.replace(AuthScreen())
                //nav?.replaceAll(AuthScreen())
            }
            CurrentScreen()
        }
    }
}