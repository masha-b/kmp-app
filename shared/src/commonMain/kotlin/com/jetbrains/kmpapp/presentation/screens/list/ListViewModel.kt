package com.jetbrains.kmpapp.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.data.MuseumObject
import com.jetbrains.kmpapp.data.MuseumRepository
import com.jetbrains.kmpapp.domain.handleMap
import com.jetbrains.kmpapp.domain.usecases.auth.AuthUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(museumRepository: MuseumRepository, authUseCase: AuthUseCase) : ViewModel() {
    val objects: StateFlow<List<MuseumObject>> =
        museumRepository.getObjects()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
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
