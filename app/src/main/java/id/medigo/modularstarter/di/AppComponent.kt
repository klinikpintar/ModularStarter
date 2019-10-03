package id.medigo.modularstarter.di

import id.medigo.auth.di.featureAuthModule
import id.medigo.home.di.featureHomeModule
import id.medigo.local.di.localModule
import id.medigo.modularstarter.BuildConfig
import id.medigo.remote.di.remoteModule
import id.medigo.repository.di.repositoryModule
import org.koin.core.module.Module

/**
 *  List all of module used by root app here
 */

val appComponent :List<Module> = listOf(
    sharedPreferenceModule,
    remoteModule(BuildConfig.BASE_URL),
    localModule,
    repositoryModule,
    featureHomeModule,
    featureAuthModule
)