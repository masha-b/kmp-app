package com.jetbrains.kmpapp.presentation.screens.apps

import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.domain.handle
import com.jetbrains.kmpapp.domain.models.apps.VkpAppType
import com.jetbrains.kmpapp.domain.usecases.apps.GetAppsByTypeUseCase
import com.jetbrains.kmpapp.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class AppsViewModel (
    private val getAppsByTypeUseCase: GetAppsByTypeUseCase,
    private val type: VkpAppType
) : BaseViewModel<AppsReducer.State, AppsReducer.Event, AppsReducer.Effect>(
    initialState = AppsReducer.State(),
    reducer = AppsReducer()
) {

    init {
        println("55555555 appsScreen appVM $this $type")
        getAppsByType()
    }


    fun getAppsByType() {
//        if (state.value.apps.isEmpty()) {
            viewModelScope.launch {
                sendEvent(AppsReducer.Event.SetLoader(true))
                getAppsByTypeUseCase.invoke(this@AppsViewModel.type.name.lowercase()).handle(
                    onSuccess = {
                        println("555555555 $it")
                        sendEvent(AppsReducer.Event.SetApps(it)) },
                    onError = { sendEvent(AppsReducer.Event.SetError(it)) }
                )
            }
//        }
    }
}