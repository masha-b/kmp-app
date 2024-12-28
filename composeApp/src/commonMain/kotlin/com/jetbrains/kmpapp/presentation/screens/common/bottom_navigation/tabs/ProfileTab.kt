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
import com.jetbrains.kmpapp.presentation.screens.ProfileScreen
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.profile
import org.jetbrains.compose.resources.stringResource

object ProfileTab : Tab {

    var onClearStack: (() -> Unit)? = null
        private set

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.profile)
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
        Navigator(ProfileScreen()) { navigator ->
            onClearStack = { navigator.popAll() }
            CurrentScreen()
        }
    }
}