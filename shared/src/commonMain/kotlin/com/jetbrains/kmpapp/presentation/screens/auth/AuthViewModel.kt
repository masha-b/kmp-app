package com.jetbrains.kmpapp.presentation.screens.auth

import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.domain.exceptions.UnauthorizedException
import com.jetbrains.kmpapp.domain.handle
import com.jetbrains.kmpapp.domain.usecases.auth.AuthUseCase
import com.jetbrains.kmpapp.presentation.base.BaseViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import kotlinx.coroutines.launch

//        sendEvent(AuthReducer.AuthEvent.ChangeLoginText("mgusev@sitesoft.ru", "Sekretnost021188"))

class AuthViewModel (
    private val authUseCase: AuthUseCase
) : BaseViewModel<AuthReducer.AuthState, AuthReducer.AuthEvent, AuthReducer.AuthEffect>(
    initialState = AuthReducer.AuthState(),
    reducer = AuthReducer()
) {
    init {
        viewModelScope.coroutineScope.launch {

        }
    }

    fun auth() {
        viewModelScope.coroutineScope.launch {
            sendEvent(AuthReducer.AuthEvent.SetLoader(true))
            authUseCase.invoke(state.value.email, state.value.password).handle(
                onSuccess = { sendEffect(AuthReducer.AuthEffect.NavigateToCallLog) },
                onError = { sendEvent(AuthReducer.AuthEvent.SetError(it)) }
            )
        }
    }
}