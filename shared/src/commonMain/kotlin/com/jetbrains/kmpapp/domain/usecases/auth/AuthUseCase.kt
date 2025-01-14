package com.jetbrains.kmpapp.domain.usecases.auth

import com.jetbrains.kmpapp.domain.repo.AuthRepo

class AuthUseCase (private val repo: AuthRepo) {

    suspend fun invoke(login: String, password: String): Result<Boolean> = repo.auth(login, password)

}