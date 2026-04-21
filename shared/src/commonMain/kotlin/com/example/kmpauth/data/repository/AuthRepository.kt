class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun login(
        username: String,
        password: String
    ): Result<UserResponse> {
        return try {
            val response = api.login(username, password)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun userInfo(
    ): Result<UserResponse> {
        return try {
            val response = api.userinfo()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}