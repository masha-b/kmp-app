package com.jetbrains.kmpapp.presentation.common.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.jetbrains.kmpapp.R
import com.jetbrains.kmpapp.presentation.common.ActionButton
import com.jetbrains.kmpapp.presentation.theme.Black
import com.jetbrains.kmpapp.presentation.theme.LightGray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String,
    isBackVisible: Boolean = false,
    actionButtons: List<ActionButton> = listOf(),
    onBackNavigation: () -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .shadow(elevation = 8.dp)
            .background(White),
        expandedHeight = 56.dp,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = Black
            )
        },
        navigationIcon = {
            if (isBackVisible)
                IconButton(onClick = { onBackNavigation.invoke() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        stringResource(R.string.back)
                    )
                }
        },
        actions = {
            LazyRow {
                items(actionButtons, key = { item -> item.key }) {
                    ToolbarButton(
                        icon = it.icon,
                        isEnabled = it.isEnable,
                        onClick = it.onClick
                    )
                }
            }
        },
        colors = TopAppBarColors(
            containerColor = White,
            actionIconContentColor = Black,
            navigationIconContentColor = Black,
            titleContentColor = Black,
            scrolledContainerColor = Black
        )
    )
}

@Composable
fun ToolbarButton(icon: Painter, isEnabled: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick, enabled = isEnabled) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = if (isEnabled) Black else LightGray
        )
    }
}