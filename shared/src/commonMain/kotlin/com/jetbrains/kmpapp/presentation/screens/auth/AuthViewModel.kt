package com.jetbrains.kmpapp.presentation.screens.auth

import com.jetbrains.kmpapp.domain.handle
import com.jetbrains.kmpapp.domain.usecases.auth.AuthUseCase
import com.jetbrains.kmpapp.domain.usecases.auth.GetAuthStateUseCase
import com.jetbrains.kmpapp.presentation.base.BaseViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel (
    private val authUseCase: AuthUseCase,
    private val getAuthStateUseCase: GetAuthStateUseCase
) : BaseViewModel<AuthReducer.AuthState, AuthReducer.AuthEvent, AuthReducer.AuthEffect>(
    initialState = AuthReducer.AuthState(),
    reducer = AuthReducer()
) {
    val authState: StateFlow<Boolean?> = getAuthStateUseCase.invoke()
        .stateIn(
            scope = viewModelScope.coroutineScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = null
        )

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