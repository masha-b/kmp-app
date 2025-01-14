package com.jetbrains.kmpapp.presentation.screens.auth

import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.domain.exceptions.UnauthorizedException
import com.jetbrains.kmpapp.domain.handle
import com.jetbrains.kmpapp.domain.usecases.auth.AuthUseCase
import com.jetbrains.kmpapp.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel (
    private val authUseCase: AuthUseCase
) : BaseViewModel<AuthReducer.AuthState, AuthReducer.AuthEvent, AuthReducer.AuthEffect>(
    initialState = AuthReducer.AuthState(),
    reducer = AuthReducer()
) {
    init {
//        sendEvent(AuthReducer.AuthEvent.ChangeLoginText("mgusev@sitesoft.ru", "Sekretnost021188"))
        viewModelScope.launch {
//            getTopicsUseCase(Unit).collect { result ->
//                sendEvent(
//                    event = ForYouEvent.UpdateTopicsLoading(
//                        isLoading = result.isLoading()
//                    )
//                )
//
//                when (result) {
//                    is Result.BusinessRuleError -> Unit
//                    is Result.Error -> Unit
//                    Result.Loading -> Unit
//                    is Result.Success -> sendEvent(
//                        event = ForYouEvent.UpdateTopics(
//                            topics = result.data
//                        )
//                    )
//                }
//            }
//        }
//
//        viewModelScope.launch {
//            getNewsUseCase(Unit).collect { result ->
//                sendEvent(
//                    event = ForYouEvent.UpdateNewsLoading(
//                        isLoading = result.isLoading()
//                    )
//                )
//
//                when (result) {
//                    is Result.BusinessRuleError -> Unit
//                    is Result.Error -> Unit
//                    Result.Loading -> Unit
//                    is Result.Success -> sendEvent(
//                        event = ForYouEvent.UpdateNews(
//                            news = result.data
//                        )
//                    )
//                }
//            }
//        }
        }
    }

    fun auth() {
        viewModelScope.launch {
            sendEvent(AuthReducer.AuthEvent.SetLoader(true))
            authUseCase.invoke(state.value.email, state.value.password).handle(
                onSuccess = { sendEffect(AuthReducer.AuthEffect.NavigateToCallLog) },
                onError = { sendEvent(AuthReducer.AuthEvent.SetError(it)) }
            )
        }
    }
}