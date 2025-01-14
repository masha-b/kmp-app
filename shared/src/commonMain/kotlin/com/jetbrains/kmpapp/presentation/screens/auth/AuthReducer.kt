package com.jetbrains.kmpapp.presentation.screens.auth

import androidx.compose.runtime.Immutable
import com.jetbrains.kmpapp.domain.exceptions.UnauthorizedException
import com.jetbrains.kmpapp.presentation.base.Reducer


class AuthReducer :
    Reducer<AuthReducer.AuthState, AuthReducer.AuthEvent, AuthReducer.AuthEffect> {
    @Immutable
    sealed class AuthEvent : Reducer.ViewEvent {
        data class ChangeEmailText(val text: String) : AuthEvent()
        data class ChangePasswordText(val text: String) : AuthEvent()
        data class SetLoader(val value: Boolean) : AuthEvent()
        data class SetError(val error: Throwable?) : AuthEvent()
        data object ChangeShowPassword : AuthEvent()
    }

    @Immutable
    sealed class AuthEffect : Reducer.ViewEffect {
        data object NavigateToCallLog : AuthEffect()
        data class Error(val error: Throwable?) : AuthEffect()
    }

    @Immutable
    data class AuthState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val isShowPassword: Boolean = false,
        val emailFieldError: String? = null
    ) : Reducer.ViewState

    override fun reduce(
        previousState: AuthState,
        event: AuthEvent
    ): Pair<AuthState, AuthEffect?> {
        return when (event) {
            is AuthEvent.ChangeEmailText -> {
                previousState.copy(
                    email = event.text,
                    emailFieldError = null
                ) to null
            }

            is AuthEvent.ChangePasswordText -> {
                previousState.copy(
                    password = event.text,
                    emailFieldError = null
                ) to null
            }

            is AuthEvent.SetLoader -> {
                previousState.copy(
                    isLoading = event.value,
                    emailFieldError = null
                ) to null
            }

            is AuthEvent.SetError -> {
                previousState.copy(
                    isLoading = false,
                    emailFieldError = (event.error as? UnauthorizedException)?.let { "Неверный email или пароль" }
                ) to if (event.error !is UnauthorizedException) AuthEffect.Error(event.error) else null
            }

            AuthEvent.ChangeShowPassword -> {
                previousState.copy(
                    isShowPassword = !previousState.isShowPassword
                ) to null
            }
        }
    }
}