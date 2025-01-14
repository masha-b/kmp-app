package com.jetbrains.kmpapp.domain.repo

import com.jetbrains.kmpapp.domain.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    suspend fun auth(login: String, password: String): Result<Boolean>
    fun clearToken()
    fun getAuthState(): Flow<Resource<Boolean>>
}