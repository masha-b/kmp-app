package com.jetbrains.kmpapp.presentation.screens.detail

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jetbrains.kmpapp.data.MuseumObject
import com.jetbrains.kmpapp.data.MuseumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DetailScreenModel(private val museumRepository: MuseumRepository) : ScreenModel {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)

    fun refresh(objectId: Int) {
        screenModelScope.launch {
            museumRepository.refresh()
        }
    }
}
