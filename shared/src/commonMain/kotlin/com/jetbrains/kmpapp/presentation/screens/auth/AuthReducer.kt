package com.jetbrains.kmpapp.presentation.screens.auth

import androidx.compose.runtime.Immutable
import com.jetbrains.kmpapp.domain.exceptions.UnauthorizedException
import com.jetbrains.kmpapp.presentation.base.Reducer


class AuthReducer :
    Reducer<AuthReducer.State, AuthReducer.Event, AuthReducer.Effect> {
    @Immutable
    sealed class Event : Reducer.ViewEvent {
        data class ChangeEmailText(val text: String) : Event()
        data class ChangePasswordText(val text: String) : Event()
        data class SetLoader(val value: Boolean) : Event()
        data class SetError(val error: Throwable?) : Event()
        data object ChangeShowPassword : Event()
    }

    @Immutable
    sealed class Effect : Reducer.ViewEffect {
        data object NavigateToCallLog : Effect()
        data class Error(val error: Throwable?) : Effect()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val isShowPassword: Boolean = false,
        val emailFieldError: String? = null
    ) : Reducer.ViewState

    override fun reduce(
        previousState: State,
        event: Event
    ): Pair<State, Effect?> {
        return when (event) {
            is Event.ChangeEmailText -> {
                previousState.copy(
                    email = event.text,
                    emailFieldError = null
                ) to null
            }

            is Event.ChangePasswordText -> {
                previousState.copy(
                    password = event.text,
                    emailFieldError = null
                ) to null
            }

            is Event.SetLoader -> {
                previousState.copy(
                    isLoading = event.value,
                    emailFieldError = null
                ) to null
            }

            is Event.SetError -> {
                previousState.copy(
                    isLoading = false,
                    emailFieldError = (event.error as? UnauthorizedException)?.let { "Неверный email или пароль" }
                ) to if (event.error !is UnauthorizedException) Effect.Error(event.error) else null
            }

            Event.ChangeShowPassword -> {
                previousState.copy(
                    isShowPassword = !previousState.isShowPassword
                ) to null
            }
        }
    }
}