package id.medigo.home.domain

import id.medigo.model.Profile
import id.medigo.repository.UserRepository
import id.medigo.repository.utils.DataCacheResource

class GetProfileUseCase(private val userRepository: UserRepository){

    operator fun invoke(username: String, shouldFetch: Boolean): DataCacheResource<Profile, Profile> {
        return userRepository.getProfileWithCache(username, shouldFetch).apply {
            result.map { it }
        }
    }

}