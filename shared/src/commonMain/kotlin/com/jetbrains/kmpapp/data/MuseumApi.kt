package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.di.Storage
import com.jetbrains.kmpapp.domain.fetchForGet
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlin.coroutines.cancellation.CancellationException

interface MuseumApi {
    suspend fun getData(): Result<List<MuseumObject>?>
}

class KtorMuseumApi(private val client: HttpClient, private val localStorage: Storage) : MuseumApi {

    companion object {
        private const val API_URL =
            "https://raw.githubusercontent.com/Kotlin/KMP-App-Template-Native/main/list.json"
    }

    override suspend fun getData(): Result<List<MuseumObject>?> {
        return try {
            Result.success(client.get(API_URL).body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}