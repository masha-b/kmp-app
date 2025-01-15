package com.jetbrains.kmpapp

import com.jetbrains.kmpapp.data.MuseumRepository
import com.jetbrains.kmpapp.data.repo.AuthRepository
import com.jetbrains.kmpapp.domain.usecases.auth.AuthUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Dependencies : KoinComponent {
    val museumRepository: MuseumRepository by inject()
    val authRepository: AuthRepository by inject()
    val authUseCase: AuthUseCase by inject()
}