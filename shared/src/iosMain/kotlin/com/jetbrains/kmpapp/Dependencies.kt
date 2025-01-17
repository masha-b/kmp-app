package com.jetbrains.kmpapp

import com.jetbrains.kmpapp.LocalStorage.LocalStorage
import com.jetbrains.kmpapp.data.MuseumRepository
import com.jetbrains.kmpapp.data.repo.AuthRepository
import com.jetbrains.kmpapp.di.Storage
import com.jetbrains.kmpapp.domain.usecases.auth.AuthUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Dependencies : KoinComponent {
    val museumRepository: MuseumRepository by inject()
    val localStorage: Storage by inject()
}

class TestLocalStorage {
    val test: String

    init {
        test = "test"
    }

}

