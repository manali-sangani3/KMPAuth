class LoginUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(username: String, password: String) =
        repo.login(username, password)
}