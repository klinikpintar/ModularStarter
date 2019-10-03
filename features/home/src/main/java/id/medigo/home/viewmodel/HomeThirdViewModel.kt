package id.medigo.home.viewmodel

import id.medigo.common.base.BaseViewModel
import id.medigo.repository.PreferenceRepository
import id.medigo.repository.utils.AppDispatchers

class HomeThirdViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val dispatchers: AppDispatchers
): BaseViewModel(preferenceRepository, dispatchers)