package com.jetbrains.kmpapp.di

import kotlinx.coroutines.flow.Flow
import org.koin.core.module.Module

interface Storage {
    var authToken: String

    fun getTokenFromPrefsAsFlow(): Flow<String>
}

expect val localStorageModule : Module