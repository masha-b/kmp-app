package com.jetbrains.kmpapp.presentation.screens.apps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.jetbrains.kmpapp.CONST.ACTION_EXIT
import com.jetbrains.kmpapp.presentation.AppViewModel
import com.jetbrains.kmpapp.presentation.common.ScreenState
import com.jetbrains.kmpapp.presentation.screens.list.ListScreen
import com.jetbrains.kmpapp.presentation.utils.debugInputPointer
import com.jetbrains.kmpapp.presentation.utils.rememberFlowWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.jetbrains.kmpapp.domain.models.apps.VkpAppType
import com.jetbrains.kmpapp.presentation.common.ActionButton
import com.jetbrains.kmpapp.presentation.common.composables.EmptyData
import com.jetbrains.kmpapp.presentation.common.composables.SearchBar
import com.jetbrains.kmpapp.presentation.common.utils.animatedListModifier
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf
import com.jetbrains.kmpapp.R
import com.jetbrains.kmpapp.presentation.theme.Gray
import com.jetbrains.kmpapp.presentation.utils.formatBuildDatetime

@Serializable
data class AppsScreen(val type: VkpAppType) : Screen {

    override val key: ScreenKey
        get() = super.key + type.name

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel =
            koinViewModel<AppsViewModel>(key = type.name, parameters = { parametersOf(type) })
        val appViewModel = koinViewModel<AppViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = rememberFlowWithLifecycle(viewModel.effect)
        val listState = rememberLazyListState()
        val context = LocalContext.current

        appViewModel.setScreenState(
            ScreenState(
                title = stringResource(type.stringRes),
                isLoading = state.isLoading,
                isBackArrowEnable = false,
                actionButtons = listOf(
                    ActionButton(
                        key = ACTION_EXIT,
                        icon = painterResource(R.drawable.ic_search),
                        onClick = {  viewModel.sendEvent(AppsReducer.Event.OnSearchIconClick) }
                    )
                ),
                onRefresh = viewModel::getAppsByType
            )
        )

        LaunchedEffect(effect) {
            effect.collect { action ->
                when (action) {
                    AppsReducer.Effect.NavigateToCallLog -> navigator.replace(ListScreen())
                    is AppsReducer.Effect.Error -> appViewModel.showError(action.error)
                    AppsReducer.Effect.ScrollListToTop -> listState.animateScrollToItem(0)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .debugInputPointer(context, viewModel.timeCapsule),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedVisibility(visible = state.isSearchBarVisible) {

                Spacer(modifier = Modifier.height(24.dp))

                SearchBar(
                    value = state.searchText,
                    onValueChanged = { viewModel.sendEvent(AppsReducer.Event.ChangeSearchText(it)) }
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (state.appsFiltered.isEmpty())
                    item { EmptyData(modifier = animatedListModifier()) }

                items(state.appsFiltered, key = { item -> item.id }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(70.dp),
                            model = "https://apps.sitesoft.ru" + it.icon + "?token=SrbxM6TgoFVufyQylcypMO9AHz6BXMOed8kyr7tqEx9xNFcyVIDWFDuAA5wg",
                            contentDescription = it.name
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(text = it.name, style = MaterialTheme.typography.titleSmall)

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(text = "${it.version} (${it.build}) от ${formatBuildDatetime(it.updatedAt)}", style = MaterialTheme.typography.labelSmall, color = Gray)

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(text = it.host, style = MaterialTheme.typography.labelSmall, color = Gray)
                        }
                    }
                }
            }
        }
    }
}