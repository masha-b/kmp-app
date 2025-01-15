package com.jetbrains.kmpapp.presentation.screens.list

import com.jetbrains.kmpapp.data.MuseumObject
import com.jetbrains.kmpapp.data.MuseumRepository
import com.jetbrains.kmpapp.domain.handleMap
import com.jetbrains.kmpapp.domain.usecases.auth.AuthUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import com.rickclephas.kmp.observableviewmodel.stateIn

class ListViewModel(museumRepository: MuseumRepository, authUseCase: AuthUseCase) : ViewModel() {
    @NativeCoroutinesState
    val objects: StateFlow<List<MuseumObject>> =
        museumRepository.getObjects()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.coroutineScope.launch {
        authUseCase.invoke("mgusev@sitesoft.ru", "Sekretnost021188").handleMap(
            onSuccess = {
                println("55555 SUCCESS $it")
            },
            onError = {
                println("55555 ERROR $it")
            }
        )
            }
    }
}
