package com.jetbrains.kmpapp.presentation.base


interface Reducer<State : Reducer.ViewState, Event : Reducer.ViewEvent, Effect : Reducer.ViewEffect> {
    interface ViewState

    interface ViewEvent

    interface ViewEffect {
        data class Error(val text: String) : ViewEffect
    }

    fun reduce(previousState: State, event: Event): Pair<State, Effect?>
}