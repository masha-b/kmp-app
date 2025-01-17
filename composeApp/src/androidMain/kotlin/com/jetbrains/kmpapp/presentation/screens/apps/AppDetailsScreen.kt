package com.jetbrains.kmpapp.presentation.screens.apps

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.jetbrains.kmpapp.presentation.AppViewModel
import com.jetbrains.kmpapp.presentation.common.ScreenState
import com.jetbrains.kmpapp.presentation.utils.debugInputPointer
import com.jetbrains.kmpapp.presentation.utils.rememberFlowWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.jetbrains.kmpapp.presentation.common.ActionButton
import com.jetbrains.kmpapp.presentation.common.composables.EmptyData
import com.jetbrains.kmpapp.presentation.common.composables.SearchBar
import com.jetbrains.kmpapp.presentation.common.utils.animatedListModifier
import org.koin.core.parameter.parametersOf
import com.jetbrains.kmpapp.R
import com.jetbrains.kmpapp.constants.CONST.ACTION_SEARCH
import com.jetbrains.kmpapp.domain.models.apps.VkpApp
import com.jetbrains.kmpapp.presentation.screens.apps.details.AppDetailsReducer
import com.jetbrains.kmpapp.presentation.screens.apps.details.AppDetailsViewModel
import com.jetbrains.kmpapp.presentation.theme.Black
import com.jetbrains.kmpapp.presentation.utils.formatBuildDatetime
import dev.icerock.moko.parcelize.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppDetailsScreen(private val app: VkpApp) : Screen, Parcelable {

    override val key: ScreenKey
        get() = super.key + app.id

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel =
            koinViewModel<AppDetailsViewModel>(
                key = app.id.toString(),
                parameters = { parametersOf(app) })
        val appViewModel = koinViewModel<AppViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = rememberFlowWithLifecycle(viewModel.effect)
        val listState = rememberLazyListState()
        val context = LocalContext.current

        appViewModel.setScreenState(
            ScreenState(
                title = app.name,
                isLoading = state.isLoading,
                actionButtons = listOf(
                    ActionButton(
                        key = ACTION_SEARCH,
                        icon = painterResource(R.drawable.ic_search),
                        onClick = { viewModel.sendEvent(AppDetailsReducer.Event.OnSearchIconClick) }
                    )
                ),
                onRefresh = viewModel::getBuilds,
                onBackPressed = navigator::pop
            )
        )

        LaunchedEffect(effect) {
            effect.collect { action ->
                when (action) {
                    is AppDetailsReducer.Effect.Error -> appViewModel.showError(action.error)
                    AppDetailsReducer.Effect.ScrollListToTop -> listState.animateScrollToItem(0)
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
                    onValueChanged = {
                        viewModel.sendEvent(
                            AppDetailsReducer.Event.ChangeSearchText(
                                it
                            )
                        )
                    }
                )
            }

            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp),
                model = state.app?.icon?.let { "https://apps.sitesoft.ru" + it + "?token=SrbxM6TgoFVufyQylcypMO9AHz6BXMOed8kyr7tqEx9xNFcyVIDWFDuAA5wg" },
                contentDescription = state.app?.name
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (state.buildsFiltered.isEmpty())
                    item { EmptyData(modifier = animatedListModifier()) }

                items(state.buildsFiltered, key = { item -> item.link }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = "${it.version} от ${formatBuildDatetime(it.datetime)}",
                            style = MaterialTheme.typography.labelMedium
                        )

                        IconButton(onClick = { onDownloadBuildClick(context, it.link) }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_download),
                                contentDescription = null,
                                tint = Black
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onDownloadBuildClick(context: Context, link: String) {
        println("5555555 $link")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        context.startActivity(intent)
    }
}