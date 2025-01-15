package com.jetbrains.kmpapp.data.remote.api

import com.jetbrains.kmpapp.data.dto.auth.AuthBody
import com.jetbrains.kmpapp.data.dto.auth.TokenDto
import com.jetbrains.kmpapp.domain.fetch
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

interface AuthApi {
    suspend fun auth(body: AuthBody): Result<TokenDto?>
}

class AuthApiImpl(private val client: HttpClient) : AuthApi {
    companion object {
        private const val API_URL = "https://vkp.sitesoft.su/api/auth"
    }

    override suspend fun auth(body: AuthBody): Result<TokenDto?> = client.fetch {
        method = HttpMethod.Post
        url(API_URL)
        contentType(ContentType.Application.Json)
        setBody(body)
    }
}