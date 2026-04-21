interface AuthRepository {

    suspend fun login(
        username: String,
        password: String
    ): Result<UserResponse>

    suspend fun userInfo(
    ): Result<UserResponse>

}