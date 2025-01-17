package com.jetbrains.kmpapp.di


import com.jetbrains.kmpapp.domain.models.apps.VkpApp
import com.jetbrains.kmpapp.domain.models.apps.VkpAppType
import com.jetbrains.kmpapp.presentation.screens.apps.details.AppDetailsViewModel
import com.jetbrains.kmpapp.presentation.screens.apps.list.AppsViewModel
import com.jetbrains.kmpapp.presentation.screens.auth.AuthViewModel
import com.jetbrains.kmpapp.presentation.screens.detail.DetailsViewModel
import com.jetbrains.kmpapp.presentation.screens.list.ListViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule: Module
    get() = module {
        factory { ListViewModel(get(), get()) }
        factory { DetailsViewModel(get()) }
        factory { AuthViewModel(get(), get()) }
        factory { (type: VkpAppType) -> AppsViewModel(get(), get(), type) }
        factory { (app: VkpApp) -> AppDetailsViewModel(get(), app) }
    }