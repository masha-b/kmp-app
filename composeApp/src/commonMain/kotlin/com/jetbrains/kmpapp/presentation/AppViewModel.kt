package com.jetbrains.kmpapp.presentation

import androidx.lifecycle.ViewModel
import com.jetbrains.kmpapp.presentation.screens.common.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(ScreenState())
    val screenState = _screenState.asStateFlow()

    fun setScreenState(state: ScreenState) {
        _screenState.update { state }
    }
}