package com.jetbrains.kmpapp.presentation.screens.auth

import com.jetbrains.kmpapp.domain.handle
import com.jetbrains.kmpapp.domain.usecases.auth.AuthUseCase
import com.jetbrains.kmpapp.domain.usecases.auth.GetAuthStateUseCase
import com.jetbrains.kmpapp.presentation.base.BaseViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel (
    private val authUseCase: AuthUseCase,
    private val getAuthStateUseCase: GetAuthStateUseCase
) : BaseViewModel<AuthReducer.State, AuthReducer.Event, AuthReducer.Effect>(
    initialState = AuthReducer.State(),
    reducer = AuthReducer()
) {
    val authState: StateFlow<Boolean?> = getAuthStateUseCase.invoke()
        .stateIn(
            viewModelScope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = null
        )

    fun auth() {
        viewModelScope.coroutineScope.launch {
            sendEvent(AuthReducer.Event.SetLoader(true))
            authUseCase.invoke(state.value.email, state.value.password).handle(
                onSuccess = { sendEffect(AuthReducer.Effect.NavigateToCallLog) },
                onError = { sendEvent(AuthReducer.Event.SetError(it)) }
            )
        }
    }
}