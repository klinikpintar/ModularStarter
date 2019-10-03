package id.medigo.auth.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import id.medigo.model.Profile
import id.medigo.repository.AuthRepository
import id.medigo.repository.utils.Resource

class GetLoginUseCase(private val authRepository: AuthRepository){

    /**
     * Place any business logic here
     */
    // TODO: Add business logic example
    suspend operator fun invoke(id: String, password: String): LiveData<Resource<Profile>> {
        return Transformations.map(authRepository.login(id, password)) {
            it
        }
    }

}