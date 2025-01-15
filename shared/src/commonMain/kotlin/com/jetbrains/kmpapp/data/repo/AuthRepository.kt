package com.jetbrains.kmpapp.data.repo

import com.jetbrains.kmpapp.data.dto.auth.AuthBody
import com.jetbrains.kmpapp.data.remote.api.AuthApi
import com.jetbrains.kmpapp.domain.Resource
import com.jetbrains.kmpapp.domain.map
import com.jetbrains.kmpapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val authApi: AuthApi
) : AuthRepo {

    override suspend fun auth(
        login: String,
        password: String
    ): Result<Boolean> = authApi.auth(AuthBody(login, password)).map {
        it?.token?.isNotBlank() == true
    }

    override fun clearToken() {
        TODO("Not yet implemented")
    }

    override fun getAuthState(): Flow<Resource<Boolean>> {
        TODO("Not yet implemented")
    }
}
