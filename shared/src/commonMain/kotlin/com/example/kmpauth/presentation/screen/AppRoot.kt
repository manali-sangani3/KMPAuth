import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AppRoot(authManager: AuthManager) {

    var user by remember { mutableStateOf<UserResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        SessionManager.loadToken()

        if (SessionManager.isLoggedIn()) {
            val userResult = authManager.getUserInfo()

            userResult
                .onSuccess {
                    user = it
                }
                .onFailure {
                    SessionManager.logout()
                }
        }
        isLoading = false
    }

    if (isLoading) return

    if (user != null) {
        HomeScreen(
            user = user!!,
            onLogout = {
                SessionManager.logout()
                user = null
            }
        )
    } else {
        LoginScreen(
            manager = authManager,
            onLoginSuccess = {
                user = it
            },
        )
    }
}