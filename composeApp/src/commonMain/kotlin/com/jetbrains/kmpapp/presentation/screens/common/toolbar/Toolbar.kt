package com.jetbrains.kmpapp.presentation.screens.common.toolbar

import androidx.compose.foundation.layout.Row
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.back
import org.jetbrains.compose.resources.stringResource

interface Toolbar {

    var setToolbar: (ToolbarState) -> Unit
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String,
    isBackVisible: Boolean = false,
    isActionsEnabled: Boolean = true,
    onBackNavigation: () -> Unit,
//    onLoginsNavigationClick: (() -> Unit)? = null,
//    onAddLoginClick: (() -> Unit)? = null,
//    onSearchClick: (() -> Unit)? = null,
//    onLogoutClick: (() -> Unit)? = null
) {
    val navigator = LocalNavigator.currentOrThrow

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
//            (Icons.AutoMirrored.Filled.ArrowBack, true, onBackNavigation)
        },
//        actions = {
//            Row {
//                onLoginsNavigationClick?.let {
//                    ActionButton(ru.mosreg.fireservice.R.drawable.ic_shield, isActionsEnabled, it)
//                }
//
//                onAddLoginClick?.let {
//                    ActionButton(ru.mosreg.fireservice.R.drawable.ic_plus, isActionsEnabled, it)
//                }
//
//                onSearchClick?.let {
//                    ActionButton(ru.mosreg.fireservice.R.drawable.ic_search, isActionsEnabled, it)
//                }
//
//                onLogoutClick?.let {
//                    ActionButton(ru.mosreg.fireservice.R.drawable.ic_logout, isActionsEnabled, it)
//                }
//            }
//        },
//        colors = TopAppBarColors(
//            containerColor = ru.mosreg.fireservice.presentation.ui.theme.Red,
//            actionIconContentColor = ru.mosreg.fireservice.presentation.ui.theme.White,
//            navigationIconContentColor = ru.mosreg.fireservice.presentation.ui.theme.White,
//            titleContentColor = ru.mosreg.fireservice.presentation.ui.theme.Red,
//            scrolledContainerColor = ru.mosreg.fireservice.presentation.ui.theme.Red
//        )
    )
}

//@Composable
//fun ActionButton(@androidx.annotation.DrawableRes iconRes: Int, isEnabled: Boolean, onClick: () -> Unit) {
//    IconButton(onClick = onClick, enabled = isEnabled) {
//        Icon(
//            painter = androidx.compose.ui.res.painterResource(id = iconRes),
//            contentDescription = null
//        )
//    }
//}