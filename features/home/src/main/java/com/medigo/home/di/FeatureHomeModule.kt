package com.medigo.home.di

import com.medigo.home.domain.GetProfileUseCase
import com.medigo.home.viewmodel.HomeMainViewModel
import com.medigo.home.viewmodel.HomeSecondViewModel
import com.medigo.home.viewmodel.HomeThirdViewModel
import com.medigo.home.viewmodel.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureHomeModule = module {
    viewModel { HomeViewModel() }
    factory { GetProfileUseCase(get()) }
    viewModel { HomeMainViewModel(get(),get()) }
    viewModel { HomeSecondViewModel() }
    viewModel { HomeThirdViewModel() }
}