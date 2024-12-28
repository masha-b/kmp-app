package com.jetbrains.kmpapp.presentation.screens.common.toolbar

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.presentation.screens.common.ActionButton
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.back
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String,
    isBackVisible: Boolean = false,
    actionButtons: List<ActionButton> = listOf(),
    onBackNavigation: () -> Unit
) {
    TopAppBar(
        expandedHeight = 56.dp,
        title = {
            Text(
                text = title,
//                style = Typography.labelLarge,
//                color = ru.mosreg.fireservice.presentation.ui.theme.White
            )
        },
        navigationIcon = {
            if (isBackVisible)
                IconButton(onClick = { onBackNavigation.invoke() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        stringResource(Res.string.back)
                    )
                }
        },
        actions = {
            LazyRow {
                items(actionButtons, key = { item -> item.key }) {
                    ToolbarButton(
                        iconRes = it.icon,
                        isEnabled = it.isEnable,
                        onClick = it.onClick
                    )
                }
            }
        },
        colors = TopAppBarColors(
            containerColor = Color.White,
            actionIconContentColor = Color.Black,
            navigationIconContentColor = Color.Black,
            titleContentColor = Color.Black,
            scrolledContainerColor = Color.Black
        )
    )
}

@Composable
fun ToolbarButton(iconRes: ImageVector, isEnabled: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick, enabled = isEnabled) {
        Icon(
            imageVector = iconRes,
            contentDescription = null,
            tint = if (isEnabled) Color.Black else Color.LightGray
        )
    }
}