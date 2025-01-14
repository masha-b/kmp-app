package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.dto.auth.AuthBody
import com.jetbrains.kmpapp.data.dto.auth.AuthResultDto
import com.jetbrains.kmpapp.domain.fetch
import com.jetbrains.kmpapp.domain.fetchForGet
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

interface MuseumApi {
    suspend fun getData(): Result<List<MuseumObject>>
    suspend fun auth(body: AuthBody): Result<AuthResultDto>
}

class KtorMuseumApi(private val client: HttpClient) : MuseumApi {
    companion object {
        private const val API_URL =
            "https://raw.githubusercontent.com/Kotlin/KMP-App-Template/main/list.json"
    }

    override suspend fun getData(): Result<List<MuseumObject>> = client.fetchForGet(API_URL)

    override suspend fun auth(body: AuthBody): Result<AuthResultDto> = client.fetch {
        method = HttpMethod.Post
        url("https://vkp.sitesoft.su/api/auth")
        contentType(ContentType.Application.Json)
        setBody(body)
    }
}