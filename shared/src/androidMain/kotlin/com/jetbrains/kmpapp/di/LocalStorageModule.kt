package com.jetbrains.kmpapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jetbrains.kmpapp.platform.LocalStorage
import org.koin.core.module.Module
import org.koin.dsl.module

private const val SHARED_PREFS = "SHARED_PREFS_APPS"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SHARED_PREFS)

actual val localStorageModule: Module
    get() = module {
        single { get<Context>().dataStore }
        single<Storage> { LocalStorage(get()) }
    }