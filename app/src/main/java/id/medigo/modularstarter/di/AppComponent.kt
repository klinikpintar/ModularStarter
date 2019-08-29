package id.medigo.modularstarter.di

import id.medigo.auth.di.featureAuthModule
import id.medigo.home.di.featureHomeModule
import id.medigo.local.di.localModule
import id.medigo.remote.di.remoteModule
import id.medigo.repository.di.repositoryModule
import org.koin.core.module.Module

val appComponent :List<Module> = listOf(
    remoteModule,
    localModule,
    repositoryModule,
    featureHomeModule,
    featureAuthModule
)