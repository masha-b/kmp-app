package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.InMemoryMuseumStorage
import com.jetbrains.kmpapp.data.KtorMuseumApi
import com.jetbrains.kmpapp.data.MuseumApi
import com.jetbrains.kmpapp.data.MuseumRepository
import com.jetbrains.kmpapp.data.MuseumStorage
import com.jetbrains.kmpapp.data.remote.api.AppsApi
import com.jetbrains.kmpapp.data.remote.api.AppsApiImpl
import com.jetbrains.kmpapp.data.remote.api.AuthApi
import com.jetbrains.kmpapp.data.remote.api.AuthApiImpl
import com.jetbrains.kmpapp.data.repo.AppsRepository
import com.jetbrains.kmpapp.data.repo.AuthRepository
import com.jetbrains.kmpapp.domain.repo.AppsRepo
import com.jetbrains.kmpapp.domain.repo.AuthRepo
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule: Module
    get() = module {
        single {
            val json = Json { ignoreUnknownKeys = true }
            HttpClient {
                install(ContentNegotiation) {
                    // TODO Fix API so it serves application/json
                    json(json, contentType = ContentType.Any)
                }
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Napier.i(message, null, "HTTP Client")
                        }
                    }
                    level = LogLevel.ALL
                }
            }.also { Napier.base(DebugAntilog()) }
        }

        single<MuseumApi> { KtorMuseumApi(get()) }
        single<MuseumStorage> { InMemoryMuseumStorage() }
        single {
            MuseumRepository(get(), get()).apply {
                initialize()
            }
        }

        single<AuthApi> { AuthApiImpl(get()) }
        single<AuthRepo> { AuthRepository(get(), get()) }

        single<AppsApi> { AppsApiImpl(get()) }
        single<AppsRepo> { AppsRepository(get()) }
    }