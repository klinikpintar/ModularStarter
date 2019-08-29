package id.medigo.auth.di

import id.medigo.auth.domain.GetLoginUseCase
import id.medigo.auth.domain.GetRegisterUseCase
import id.medigo.auth.viewmodel.LoginViewModel
import id.medigo.auth.viewmodel.RegisterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureAuthModule = module {
    factory { GetLoginUseCase(get()) }
    factory { GetRegisterUseCase(get()) }
    viewModel { LoginViewModel(get(),get()) }
    viewModel { RegisterViewModel(get(),get()) }
}