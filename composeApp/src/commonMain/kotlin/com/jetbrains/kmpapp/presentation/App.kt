package com.jetbrains.kmpapp.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.internal.BackHandler
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.jetbrains.kmpapp.CloseApp
import com.jetbrains.kmpapp.presentation.screens.common.composables.Progress
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.TabNavigationItem
import com.jetbrains.kmpapp.presentation.screens.common.toolbar.Toolbar
import com.jetbrains.kmpapp.presentation.screens.common.ScreenState
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs.HomeTab
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs.NewsTab
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs.ProfileTab
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs.SettingsTab
import com.jetbrains.kmpapp.presentation.screens.common.bottom_navigation.tabs.TasksTab
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@OptIn(InternalVoyagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        val appViewModel = koinViewModel<AppViewModel>()
        val screenState by appViewModel.screenState.collectAsStateWithLifecycle(ScreenState())
        val scope = rememberCoroutineScope()

        var isNeedCloseApp: Boolean by remember { mutableStateOf(false) }

        var isRefreshing: Boolean by remember { mutableStateOf(false) }
//        var onRefresh: (() -> Unit)? by remember { mutableStateOf(null) }
        val pullToRefreshState = rememberPullToRefreshState()
        val snackbarHostState = remember { SnackbarHostState() }

        if (isNeedCloseApp) CloseApp()

        screenState.error?.let {
            scope.launch {
                screenState.onErrorHandled?.invoke()
                snackbarHostState.showSnackbar(it)
            }
        }

        TabNavigator(HomeTab) { navigator ->

            BackHandler(enabled = true) {
                if (navigator.current.key == HomeTab.key) {
                    isNeedCloseApp = true
                } else
                    navigator.current = HomeTab
            }

            with(screenState) {
                Scaffold(
                    modifier = Modifier.background(Color.Transparent),
                    topBar = {
                        if (isEnable) {
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
                                .padding(), // TODO если подать innerPadding, то вырез у FAB не будет прозрачным
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
                                        color = Color.Black,
                                        containerColor = LightGray
                                    )
                                }
                            }
                        ) {
                            CurrentTab()
                            if (isLoading) Progress()
                        }
                    },
                    floatingActionButton = {
                        AnimatedVisibility(isBottomNavigationEnable) {
                            FloatingActionButton(
                                onClick = { navigator.current = NewsTab },
                                modifier = Modifier,
                                shape = RoundedCornerShape(30)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.List,
                                    contentDescription = null,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    },
                    isFloatingActionButtonDocked = true,
                    floatingActionButtonPosition = FabPosition.Center,
                    bottomBar = {
                        AnimatedVisibility(isBottomNavigationEnable) {
                            BottomAppBar(
                                cutoutShape = RoundedCornerShape(30)
                            ) {
                                TabNavigationItem(HomeTab) { HomeTab.onClearStack?.invoke() }
                                TabNavigationItem(SettingsTab) { SettingsTab.onClearStack?.invoke() }
                                TabNavigationItem(
                                    NewsTab,
                                    isEnable = false
                                ) { SettingsTab.onClearStack?.invoke() }
                                TabNavigationItem(ProfileTab) { ProfileTab.onClearStack?.invoke() }
                                TabNavigationItem(TasksTab) { ProfileTab.onClearStack?.invoke() }
                            }
                        }
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                )
            }
        }
    }
}