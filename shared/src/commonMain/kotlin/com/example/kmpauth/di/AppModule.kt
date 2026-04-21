object AppModule {

    private val authApi by lazy { AuthApi(HttpClientProvider.client) }

    private val repository by lazy { AuthRepositoryImpl(authApi) }

    private val loginUseCase by lazy { LoginUseCase(repository) }
    private val userInfoUseCase by lazy { UserInfoUseCase(repository) }

    val authManager by lazy {
        AuthManager(loginUseCase, userInfoUseCase)
    }
}