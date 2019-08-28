package com.medigo.auth.di

import com.medigo.auth.domain.GetLoginUseCase
import com.medigo.auth.domain.GetRegisterUseCase
import com.medigo.auth.viewmodel.LoginViewModel
import com.medigo.auth.viewmodel.RegisterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureAuthModule = module {
    factory { GetLoginUseCase(get()) }
    factory { GetRegisterUseCase(get()) }
    viewModel { LoginViewModel(get(),get()) }
    viewModel { RegisterViewModel(get(),get()) }
}