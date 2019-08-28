package com.medigo.repository.di

import com.medigo.repository.*
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
    factory { UserRepositoryImpl(get(), get()) as UserRepository }
    factory { AuthRepositoryImpl(get()) as AuthRepository }
}