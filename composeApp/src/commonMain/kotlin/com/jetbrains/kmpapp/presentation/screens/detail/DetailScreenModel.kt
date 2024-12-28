package com.jetbrains.kmpapp.presentation.screens.detail

import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.core.model.ScreenModel
import com.jetbrains.kmpapp.data.MuseumObject
import com.jetbrains.kmpapp.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class DetailScreenModel(private val museumRepository: MuseumRepository) : ViewModel() {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
