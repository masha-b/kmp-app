package com.jetbrains.kmpapp.domain.usecases.auth

import com.jetbrains.kmpapp.domain.repo.AuthRepo

class LogoutUseCase (private val repo: AuthRepo) {

    fun invoke() = repo.clearToken()

}