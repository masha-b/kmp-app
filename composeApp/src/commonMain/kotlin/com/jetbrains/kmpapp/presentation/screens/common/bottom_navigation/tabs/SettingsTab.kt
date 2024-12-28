package com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.jetbrains.kmpapp.presentation.screens.SettingsScreen
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.stringResource

object SettingsTab : Tab {

    var onClearStack: (() -> Unit)? = null
        private set

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.settings)
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
        Navigator(SettingsScreen()) { navigator ->
            onClearStack = { navigator.popAll() }
            CurrentScreen()
        }
    }
}