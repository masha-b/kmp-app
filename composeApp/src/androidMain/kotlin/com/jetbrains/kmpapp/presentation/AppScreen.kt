package com.jetbrains.kmpapp.presentation

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.internal.BackHandler
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.jetbrains.kmpapp.domain.exceptions.ServerException
import com.jetbrains.kmpapp.domain.exceptions.UnauthorizedException
import com.jetbrains.kmpapp.presentation.common.composables.Progress
import com.jetbrains.kmpapp.presentation.common.bottom_navigation.TabNavigationItem
import com.jetbrains.kmpapp.presentation.common.toolbar.Toolbar
import com.jetbrains.kmpapp.presentation.common.ScreenState
import com.jetbrains.kmpapp.presentation.common.bottom_navigation.tabs.AndroidTab
import com.jetbrains.kmpapp.presentation.common.bottom_navigation.tabs.IosTab
import com.jetbrains.kmpapp.presentation.common.bottom_navigation.tabs.WindowsTab
import com.jetbrains.kmpapp.presentation.theme.Orange
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


@OptIn(InternalVoyagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val context = LocalContext.current
    val appViewModel = koinViewModel<AppViewModel>()
    val screenState by appViewModel.screenState.collectAsStateWithLifecycle(
        ScreenState(
            isEnable = false,
            isBottomNavigationEnable = false
        )
    )
    val scope = rememberCoroutineScope()

    var isRefreshing: Boolean by remember { mutableStateOf(false) }
//        var onRefresh: (() -> Unit)? by remember { mutableStateOf(null) }
    val pullToRefreshState = rememberPullToRefreshState()
    val snackbarHostState = remember { SnackbarHostState() }

//    val androidTab =

    TabNavigator(AndroidTab) { navigator ->

        BackHandler(enabled = true) {
            if (navigator.current.key == AndroidTab.key) {
                (context as? Activity)?.finishAffinity()
            } else
                navigator.current = AndroidTab
        }

        screenState.error?.let {
            scope.launch {
                screenState.onErrorHandled?.invoke()
                val errorText: String? = when (it) {
                    is UnauthorizedException -> {
                        clearTabsBackstack()
                        navigator.current = AndroidTab.apply { openAuthScreen?.invoke() }
                        if (it.isShowError) "Ошибка авторизации" else null
                    }
                    is ServerException -> it.error ?: "Непредвиденная серверная ошибка"
                    is SocketTimeoutException -> "Превышено время ожидания сервера"
                    is UnknownHostException, is ConnectException, is IOException -> "Отсутствует подключение к Интернету"
                    else -> "Непредвиденная ошибка"
                }
                errorText?.let { snackbarHostState.showSnackbar(it) }
            }
        }

        with(screenState) {
            Scaffold(
                modifier = Modifier.background(Color.Transparent),
                topBar = {
                    AnimatedVisibility(
                        visible = isEnable,
                        enter = slideIn(initialOffset = { IntOffset(0, 100) }),
                        exit = slideOut(targetOffset = { IntOffset(0, 100) })
                    ) {
                        Toolbar(
                            title = title,
                            isBackVisible = isBackArrowEnable,
                            onBackNavigation = { onBackPressed?.invoke() },
                            actionButtons = actionButtons
                        )
                    }
                },
                content = { innerPadding ->
                    PullToRefreshBox(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(White)
                            .padding(innerPadding),
                        state = pullToRefreshState,
                        isRefreshing = isRefreshing,
                        onRefresh = {
                            onRefresh?.invoke()
                            scope.launch {
                                isRefreshing = true
                                delay(500)
                                isRefreshing = false
                            }
                        },
                        indicator = {
                            if (onRefresh != null) {
                                PullToRefreshDefaults.Indicator(
                                    modifier = Modifier.align(Alignment.TopCenter),
                                    state = pullToRefreshState,
                                    isRefreshing = isRefreshing,
                                    color = Orange,
                                    containerColor = White
                                )
                            }
                        }
                    ) {
                        CurrentTab()
                        if (isLoading) Progress()
                    }
                },
                bottomBar = {
                    AnimatedVisibility(
                        visible = isBottomNavigationEnable,
                        enter = slideIn(initialOffset = { IntOffset(0, 100) }),
                        exit = slideOut(targetOffset = { IntOffset(0, 100) })
                    ) {
                        BottomAppBar(
                            modifier = Modifier
                                .shadow(elevation = 8.dp)
                                .background(White),
                            containerColor = White
                        ) {
                            TabNavigationItem(AndroidTab) { AndroidTab.onClearStack?.invoke() }
                            TabNavigationItem(IosTab) { IosTab.onClearStack?.invoke() }
                            TabNavigationItem(WindowsTab) { WindowsTab.onClearStack?.invoke() }
                        }
                    }
                },
                snackbarHost = { SnackbarHost(snackbarHostState) }
            )
        }
    }
}

private fun clearTabsBackstack() {
    AndroidTab.onClearStack?.invoke()
    IosTab.onClearStack?.invoke()
    WindowsTab.onClearStack?.invoke()
}