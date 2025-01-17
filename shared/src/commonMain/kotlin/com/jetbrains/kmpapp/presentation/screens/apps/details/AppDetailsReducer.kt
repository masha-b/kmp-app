package com.jetbrains.kmpapp.presentation.screens.apps.details

import androidx.compose.runtime.Immutable
import com.jetbrains.kmpapp.domain.exceptions.UnauthorizedException
import com.jetbrains.kmpapp.domain.models.apps.VkpApp
import com.jetbrains.kmpapp.domain.models.apps.VkpBuild
import com.jetbrains.kmpapp.presentation.base.Reducer


class AppDetailsReducer :
    Reducer<AppDetailsReducer.State, AppDetailsReducer.Event, AppDetailsReducer.Effect> {
    @Immutable
    sealed class Event : Reducer.ViewEvent {
        data class SetLoader(val value: Boolean) : Event()
        data class SetError(val error: Throwable?) : Event()
        data class SetBuilds(val list: List<VkpBuild>) : Event()
        data object OnSearchIconClick : Event()
        data class ChangeSearchText(val text: String) : Event()
    }

    @Immutable
    sealed class Effect : Reducer.ViewEffect {
        data class Error(val error: Throwable?) : Effect()
        data object ScrollListToTop : Effect()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val app: VkpApp? = null,
        val builds: List<VkpBuild> = emptyList(),
        val buildsFiltered: List<VkpBuild> = emptyList(),
        val isSearchBarVisible: Boolean = false,
        val searchText: String = ""
    ) : Reducer.ViewState

    override fun reduce(
        previousState: State,
        event: Event
    ): Pair<State, Effect?> {
        return when (event) {
            is Event.SetLoader -> {
                previousState.copy(
                    isLoading = event.value
                ) to null
            }

            is Event.SetError -> {
                previousState.copy(
                    isLoading = false
                ) to if (event.error !is UnauthorizedException) Effect.Error(event.error) else null
            }

            is Event.SetBuilds -> {
                previousState.copy(
                    isLoading = false,
                    builds = event.list,
                    buildsFiltered = event.list.filter { it.version.contains(previousState.searchText, true) }
                ) to null
            }

            Event.OnSearchIconClick -> {
                previousState.copy(
                    isSearchBarVisible = !previousState.isSearchBarVisible
                ) to null
            }

            is Event.ChangeSearchText -> {
                previousState.copy(
                    searchText = event.text,
                    buildsFiltered = previousState.builds.filter { it.version.contains(event.text, true) }
                ) to Effect.ScrollListToTop
            }
        }
    }
}