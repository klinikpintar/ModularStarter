package id.medigo.remote

import id.medigo.remote.api.UserService

class UserDataSource (var apiService: UserService){

    suspend fun postLogin(username: String, password: String) =
            apiService.login(username, password)

    suspend fun postRegistration(username: String, password: String) =
            apiService.register(username, password)

    suspend fun fetchUser() =
            apiService.getUserProfile()



    suspend fun fetchRepos(username: String) =
            apiService.getRepos(username)

}