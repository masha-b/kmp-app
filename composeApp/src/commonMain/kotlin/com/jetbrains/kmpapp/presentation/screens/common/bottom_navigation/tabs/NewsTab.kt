package com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.jetbrains.kmpapp.presentation.screens.NewsScreen
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.news
import org.jetbrains.compose.resources.stringResource

object NewsTab : Tab {

    var onClearStack: (() -> Unit)? = null
        private set

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.news)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = null
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(NewsScreen()) { navigator ->
            onClearStack = { navigator.popAll() }
            CurrentScreen()
        }
    }
}