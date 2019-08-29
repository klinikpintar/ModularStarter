package id.medigo.home.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import id.medigo.model.Profile
import id.medigo.repository.UserRepository
import id.medigo.repository.utils.Resource

class GetProfileUseCase(private val userRepository: UserRepository){

    suspend operator fun invoke(shouldFetch: Boolean): LiveData<Resource<Profile>> {
        return Transformations.map(userRepository.getProfileWithCache(shouldFetch)) {
            it
        }
    }

}