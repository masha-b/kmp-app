package com.jetbrains.kmpapp.data.repo

import com.jetbrains.kmpapp.data.dto.auth.AuthBody
import com.jetbrains.kmpapp.data.remote.api.AuthApi
import com.jetbrains.kmpapp.di.Storage
import com.jetbrains.kmpapp.domain.map
import com.jetbrains.kmpapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepository(
    private val authApi: AuthApi,
    private val localStorage: Storage
) : AuthRepo {

    override suspend fun auth(
        login: String,
        password: String
    ): Result<Boolean> = authApi.auth(AuthBody(login, password)).map {
        println("5555555 ${it?.token}")
        localStorage.authToken = it?.token.orEmpty()
        println("5555555 ${localStorage.authToken}")
        it?.token?.isNotBlank() == true
    }

    override fun clearToken() {
        localStorage.authToken = ""
    }

    override fun getAuthState(): Flow<Boolean> =
        localStorage.getTokenFromPrefsAsFlow().map { it.isNotEmpty() }
}