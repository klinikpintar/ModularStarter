package id.medigo.remote

class DataStore (var apiService: ApiService){

    suspend fun postLoginAsync(id: String, password: String) =
            apiService.login(id, password)

    suspend fun postRegistrationAsync(id: String, password: String) =
            apiService.register(id, password)

    suspend fun fetchUserAsync() =
            apiService.getUserProfile()

    suspend fun postUserAsync(inputForm: HashMap<String, Any>) =
            apiService.editProfile(inputForm)

}