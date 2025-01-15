package com.jetbrains.kmpapp.presentation

import androidx.lifecycle.ViewModel
import com.jetbrains.kmpapp.domain.exceptions.ServerException
import com.jetbrains.kmpapp.domain.exceptions.UnauthorizedException
import com.jetbrains.kmpapp.presentation.common.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AppViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(ScreenState())
    val screenState = _screenState.asStateFlow()

    fun setScreenState(state: ScreenState) {
        _screenState.update { state }
    }

    fun showError(error: Throwable?) {
        _screenState.update {
            it.copy(
                error = when (error) {
                    is UnauthorizedException -> "Ошибка авторизации"
                    is ServerException -> error.error ?: "Непредвиденная серверная ошибка"
                    is SocketTimeoutException -> "Превышено время ожидания сервера"
                    is UnknownHostException, is ConnectException, is IOException -> "Отсутствует подключение к Интернету"
                    else -> "Непредвиденная ошибка"
                }
            )
        }
    }
}