package com.medigo.home.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.medigo.model.Profile
import com.medigo.repository.UserRepository
import com.medigo.repository.utils.Resource

class GetProfileUseCase(private val userRepository: UserRepository){

    suspend operator fun invoke(shouldFetch: Boolean): LiveData<Resource<Profile>> {
        return Transformations.map(userRepository.getProfileWithCache(shouldFetch)) {
            it
        }
    }

}