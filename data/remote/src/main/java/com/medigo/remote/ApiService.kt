package com.medigo.remote

import com.medigo.model.Profile
import retrofit2.http.*

interface ApiService {

    // Authentication
    // -------------- //

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") id: String,
        @Field("password") password: String
    ): Profile

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") id: String,
        @Field("password") password: String
    ): Profile

    // -------------- //
    // Profile //
    // ------- //

    @GET("me")
    suspend fun getUserProfile(): Profile

    @PUT("me")
    suspend fun editProfile(@Body inputForm: HashMap<String, Any>): Profile


}