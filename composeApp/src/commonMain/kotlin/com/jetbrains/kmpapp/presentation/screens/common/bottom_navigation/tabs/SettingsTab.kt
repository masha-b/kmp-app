package com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.jetbrains.kmpapp.presentation.screens.SettingsScreen
import com.jetbrains.kmpapp.presentation.screens.common.toolbar.Toolbar
import com.jetbrains.kmpapp.presentation.screens.common.toolbar.ToolbarState
import com.jetbrains.kmpapp.presentation.screens.list.ListScreen

object SettingsTab : Tab, Toolbar {

    override var setToolbar: (ToolbarState) -> Unit = {}

    override val options: TabOptions
        @Composable
        get() {
            val title = "Настройки"
            val icon = rememberVectorPainter(Icons.Default.Settings)

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
        Navigator(ListScreen{ setToolbar.invoke(it.copy(title = options.title)) }, onBackPressed = {
            println("5555555 ${it.key}")
            it.key != ListScreen::class.simpleName
        })
    }
}