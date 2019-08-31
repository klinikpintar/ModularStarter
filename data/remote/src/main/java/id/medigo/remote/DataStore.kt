package id.medigo.remote

class DataStore (var apiService: ApiService){

    fun postLogin(username: String, password: String) =
            apiService.login(username
//                , password
            )

    fun postRegistration(username: String, password: String) =
            apiService.register(username, password)

    fun fetchUser(username: String) =
            apiService.getUserProfile(username)

    fun fetchRepos(username: String) =
            apiService.getRepos(username)

}