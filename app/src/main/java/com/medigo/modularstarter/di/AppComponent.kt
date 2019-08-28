package com.medigo.modularstarter.di

import com.medigo.auth.di.featureAuthModule
import com.medigo.home.di.featureHomeModule
import com.medigo.local.di.localModule
import com.medigo.remote.di.remoteModule
import com.medigo.repository.di.repositoryModule
import org.koin.core.module.Module

val appComponent :List<Module> = listOf(
    remoteModule,
    localModule,
    repositoryModule,
    featureHomeModule,
    featureAuthModule
)