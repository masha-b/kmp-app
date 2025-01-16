package com.jetbrains.kmpapp.domain.usecases.auth

import com.jetbrains.kmpapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow

class GetAuthStateUseCase (private val repo: AuthRepo) {

    fun invoke(): Flow<Boolean> = repo.getAuthState()

}