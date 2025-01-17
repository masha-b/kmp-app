package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.LocalStorage.LocalStorage
import org.koin.core.module.Module
import org.koin.dsl.module


actual val localStorageModule: Module
    get() = module {
        single<Storage> {
            LocalStorage()
        }
    }