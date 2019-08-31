package id.medigo.auth.domain

import id.medigo.model.Profile
import id.medigo.repository.AuthRepository
import id.medigo.repository.utils.DataCallResource

class GetLoginUseCase(private val authRepository: AuthRepository){

    /**
     * Place any business logic here
     */
    // TODO: Add business logic example
    operator fun invoke(id: String, password: String): DataCallResource<Profile, Profile> {
        return authRepository.login(id, password).apply {
            result.map {
                it
            }
        }
    }

}