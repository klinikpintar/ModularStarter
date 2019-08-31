package id.medigo.remote

import id.medigo.model.Profile
import id.medigo.model.Repos
import io.reactivex.Maybe
import retrofit2.http.*

interface ApiService {

    /**
     * Dumb fake api service, replace with your own
     */

    // AUTH SERVICE

    @GET("users/{username}")
    fun login(
        @Path("username") id: String
//        , @Query("password") password: String
    ): Maybe<Profile>

    @GET("users/{username}")
    fun register(
        @Path("username") id: String,
        @Query("password") password: String
    ): Maybe<Profile>

    // PROFILE SERVICE

    @GET("users/{username}")
    fun getUserProfile(
        @Path("username") id: String
    ): Maybe<Profile>

    // REPOS SERVICE

    @GET("users/{username}/repos")
    fun getRepos(
        @Path("username") username: String
    ): Maybe<MutableList<Repos>>

}