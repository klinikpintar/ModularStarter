package id.medigo.home.di

import id.medigo.home.domain.GetProfileUseCase
import id.medigo.home.viewmodel.HomeMainViewModel
import id.medigo.home.viewmodel.HomeSecondViewModel
import id.medigo.home.viewmodel.HomeThirdViewModel
import id.medigo.home.viewmodel.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureHomeModule = module {
    viewModel { HomeViewModel() }
    factory { GetProfileUseCase(get()) }
    viewModel { HomeMainViewModel(get(),get()) }
    viewModel { HomeSecondViewModel() }
    viewModel { HomeThirdViewModel() }
}