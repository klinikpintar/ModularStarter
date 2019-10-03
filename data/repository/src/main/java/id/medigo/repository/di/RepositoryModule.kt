package id.medigo.repository.di

import id.medigo.repository.*
import id.medigo.repository.utils.AppDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
    factory { UserRepositoryImpl(get(), get(), get(), get()) as UserRepository }
    factory { AuthRepositoryImpl(get(), get(), get(), get()) as AuthRepository }
    factory { PreferenceRepositoryImpl(get(), get()) as PreferenceRepository }
}