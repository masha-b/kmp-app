package com.jetbrains.kmpapp.presentation.screens.apps.details

import com.jetbrains.kmpapp.domain.handle
import com.jetbrains.kmpapp.domain.models.apps.VkpApp
import com.jetbrains.kmpapp.domain.usecases.apps.GetBuildsByAppIdUseCase
import com.jetbrains.kmpapp.presentation.base.BaseViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import kotlinx.coroutines.launch

class AppDetailsViewModel(
    private val getBuildsByAppIdUseCase: GetBuildsByAppIdUseCase,
    private val app: VkpApp
) : BaseViewModel<AppDetailsReducer.State, AppDetailsReducer.Event, AppDetailsReducer.Effect>(
    initialState = AppDetailsReducer.State(app = app),
    reducer = AppDetailsReducer()
) {

    init {
        getBuilds()
    }


    fun getBuilds() {
        state.value.app?.id?.let {
            viewModelScope.coroutineScope.launch {
                sendEvent(AppDetailsReducer.Event.SetLoader(true))
                getBuildsByAppIdUseCase.invoke(it).handle(
                    onSuccess = { sendEvent(AppDetailsReducer.Event.SetBuilds(it)) },
                    onError = { sendEvent(AppDetailsReducer.Event.SetError(it)) }
                )
            }
        }
    }
}