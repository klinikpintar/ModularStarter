package id.medigo.auth.domain

import id.medigo.model.Profile
import id.medigo.repository.AuthRepository
import id.medigo.repository.utils.DataCallResource

class GetRegisterUseCase(private val authRepository: AuthRepository){
    /**
     * Place any business logic here
     */
    operator fun invoke(id: String, password: String): DataCallResource<Profile, Profile> {
        return authRepository.register(id, password).apply {
            result.map {
                it
            }
        }
    }

}