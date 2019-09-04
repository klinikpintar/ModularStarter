package id.medigo.home.viewmodel

import id.medigo.common.base.BaseViewModel
import id.medigo.repository.PreferenceRepository
import id.medigo.repository.RxSchedulers

class HomeViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val schedulers: RxSchedulers
) : BaseViewModel(preferenceRepository, schedulers)