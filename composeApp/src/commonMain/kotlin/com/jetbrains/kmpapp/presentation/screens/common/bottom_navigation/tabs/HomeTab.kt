package com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.jetbrains.kmpapp.presentation.screens.common.toolbar.Toolbar
import com.jetbrains.kmpapp.presentation.screens.common.toolbar.ToolbarState
import com.jetbrains.kmpapp.presentation.screens.list.ListScreen

object HomeTab : Tab, Toolbar {

    override var setToolbar: (ToolbarState) -> Unit = {}

    override val options: TabOptions
        @Composable
        get() {
            val title = "Главная"
            val icon = rememberVectorPainter(Icons.Default.Home)

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
        Navigator(ListScreen { setToolbar.invoke(it) }) { navigator ->
            CurrentScreen()
        }
    }
}