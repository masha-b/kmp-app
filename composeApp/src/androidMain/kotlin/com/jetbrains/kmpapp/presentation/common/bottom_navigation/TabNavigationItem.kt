package com.jetbrains.kmpapp.presentation.common.bottom_navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.jetbrains.kmpapp.presentation.theme.Gray
import com.jetbrains.kmpapp.presentation.theme.Orange

@Composable
fun RowScope.TabNavigationItem(tab: Tab, isEnable: Boolean = true, onClearStack: () -> Unit) {
    val tabNavigator = LocalTabNavigator.current
    var isSelected: Boolean by remember { mutableStateOf(false) }

    BottomNavigationItem(
        modifier = Modifier,
        selected = isSelected,
        enabled = isEnable,
        onClick = {
            if (isSelected) onClearStack.invoke()
            tabNavigator.current = tab
        }.also { isSelected = tabNavigator.current == tab },
        label = {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = tab.options.title,
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) Orange else Gray
            )
        },
        icon = {
            tab.options.icon?.let {
                Icon(
                    painter = it,
                    contentDescription = tab.options.title,
                    tint = if (isSelected) Orange else Gray
                )
            }
        },
        alwaysShowLabel = true
    )
}