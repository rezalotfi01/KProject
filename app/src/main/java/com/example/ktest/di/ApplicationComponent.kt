package com.example.ktest.di

import org.koin.core.module.Module

val appComponent: List<Module> = listOf(applicationModule, networkModule, databaseModule, sharedPreferencesModule
    , reactiveModule, repositoryModule)