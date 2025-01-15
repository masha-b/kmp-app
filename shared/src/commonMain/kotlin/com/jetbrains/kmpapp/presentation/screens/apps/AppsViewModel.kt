package com.jetbrains.kmpapp.presentation.screens.apps

import com.jetbrains.kmpapp.domain.handle
import com.jetbrains.kmpapp.domain.models.apps.VkpAppType
import com.jetbrains.kmpapp.domain.usecases.apps.GetAppsByTypeUseCase
import com.jetbrains.kmpapp.presentation.base.BaseViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import kotlinx.coroutines.launch

class AppsViewModel(
    private val getAppsByTypeUseCase: GetAppsByTypeUseCase,
    private val type: VkpAppType
) : BaseViewModel<AppsReducer.State, AppsReducer.Event, AppsReducer.Effect>(
    initialState = AppsReducer.State(),
    reducer = AppsReducer()
) {

    init {
        getAppsByType()
    }


    fun getAppsByType() {
        viewModelScope.coroutineScope.launch {
            sendEvent(AppsReducer.Event.SetLoader(true))
            getAppsByTypeUseCase.invoke(this@AppsViewModel.type.name.lowercase()).handle(
                onSuccess = { sendEvent(AppsReducer.Event.SetApps(it)) },
                onError = { sendEvent(AppsReducer.Event.SetError(it)) }
            )
        }
    }
}