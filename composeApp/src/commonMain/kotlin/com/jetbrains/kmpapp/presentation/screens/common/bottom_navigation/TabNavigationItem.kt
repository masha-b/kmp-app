package com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab

@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    var isSelected: Boolean by remember { mutableStateOf(false) }

    BottomNavigationItem(
        selected = isSelected,
        onClick = { tabNavigator.current = tab }.also { isSelected = tabNavigator.current == tab },
        label = {
            Text(
                text = tab.options.title,
                color = if (isSelected) Color.White else Color.Gray
            )
        },
        icon = {
            tab.options.icon?.let {
                Icon(
                    painter = it,
                    contentDescription = tab.options.title,
                    tint = if (isSelected) Color.White else Color.Gray
                )
            }
        }
    )
}