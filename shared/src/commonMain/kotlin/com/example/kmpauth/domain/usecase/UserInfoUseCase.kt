class UserInfoUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke() =
        repo.userInfo()
}