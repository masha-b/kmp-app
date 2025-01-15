package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.domain.fetchForGet
import io.ktor.client.HttpClient

interface MuseumApi {
    suspend fun getData(): Result<List<MuseumObject>>
}

class KtorMuseumApi(private val client: HttpClient) : MuseumApi {
    companion object {
        private const val API_URL =
            "https://raw.githubusercontent.com/Kotlin/KMP-App-Template/main/list.json"
    }

    override suspend fun getData(): Result<List<MuseumObject>> = client.fetchForGet(API_URL)

}