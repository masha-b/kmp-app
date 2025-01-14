package com.jetbrains.kmpapp.presentation.screens.apps

import androidx.compose.runtime.Immutable
import com.jetbrains.kmpapp.domain.exceptions.UnauthorizedException
import com.jetbrains.kmpapp.domain.models.apps.VkpApp
import com.jetbrains.kmpapp.presentation.base.Reducer
import com.jetbrains.kmpapp.presentation.screens.apps.AppsReducer.Effect.*


class AppsReducer :
    Reducer<AppsReducer.State, AppsReducer.Event, AppsReducer.Effect> {
    @Immutable
    sealed class Event : Reducer.ViewEvent {
        data class SetLoader(val value: Boolean) : Event()
        data class SetError(val error: Throwable?) : Event()
        data class SetApps(val list: List<VkpApp>) : Event()
        data object OnSearchIconClick : Event()
        data class ChangeSearchText(val text: String) : Event()
    }

    @Immutable
    sealed class Effect : Reducer.ViewEffect {
        data object NavigateToCallLog : Effect()
        data class Error(val error: Throwable?) : Effect()
        data object ScrollListToTop : Effect()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val apps: List<VkpApp> = emptyList(),
        val appsFiltered: List<VkpApp> = emptyList(),
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
                ) to if (event.error !is UnauthorizedException) Error(event.error) else null
            }

            is Event.SetApps -> {
                previousState.copy(
                    isLoading = false,
                    apps = event.list,
                    appsFiltered = event.list.filter { it.name.contains(previousState.searchText, true) }
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
                    appsFiltered = previousState.apps.filter { it.name.contains(event.text, true) }
                ) to ScrollListToTop
            }
        }
    }
}