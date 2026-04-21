class AuthManager(
    private val loginUseCase: LoginUseCase,
    private val userInfoUseCase: UserInfoUseCase,
) {

    suspend fun login(username: String, password: String): Result<Unit> {
        return runCatching {

            val response = loginUseCase(username, password).getOrThrow()

            response.accessToken?.let {
                SessionManager.saveToken(it)
            }
        }
    }

    suspend fun getUserInfo(): Result<UserResponse> {
        return runCatching {

            userInfoUseCase().getOrThrow()
        }
    }


}