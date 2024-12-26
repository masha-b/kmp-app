package com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.BaseTab
import com.jetbrains.kmpapp.presentation.screens.list.ListScreen

object ProfileTab : Tab, BaseTab() {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Профиль"
            val icon = rememberVectorPainter(Icons.Default.Person)

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
        Navigator(ListScreen { setToolbar(it) }) { navigator ->
            onClearStack = { navigator.popAll() }
            CurrentScreen()
        }
    }
}