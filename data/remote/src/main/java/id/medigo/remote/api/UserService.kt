package id.medigo.remote.api

import id.medigo.model.Profile
import id.medigo.model.Repos
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    /**
     * Dumb fake api service, replace with your own
     */

    @GET("users/{username}")
    suspend fun login(
        @Path("username") id: String,
        @Query("password") password: String
    ): Response<Profile>

    @GET("users/{username}")
    suspend fun register(
        @Path("username") id: String,
        @Query("password") password: String
    ): Response<Profile>

    // PROFILE SERVICE

    @GET("users")
    suspend fun getUserProfile(): Response<Profile>

    // REPOS SERVICE

    @GET("users/repos")
    suspend fun getRepos(
        @Path("username") username: String
    ): Response<MutableList<Repos>>

}