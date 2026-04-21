import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.material3.*

@Composable
fun LoginScreen(
    manager: AuthManager, onLoginSuccess: (
        UserResponse?,
    ) -> Unit
) {

    val scope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var loggedInUser by remember { mutableStateOf<UserResponse?>(null) }
    var errorText by remember { mutableStateOf("") }

    if (loggedInUser != null) {
        HomeScreen(
            user = loggedInUser!!,
            onLogout = {
                loggedInUser = null
                username = ""
                password = ""
                errorText = ""
            }
        )
        return
    }

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
        ) {

            Text(
                text = "Welcome Back 👋",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer16()

            Text(
                text = "Login to continue",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer24()

            LoginCard(
                username = username,
                password = password,
                onUsernameChange = { username = it },
                onPasswordChange = { password = it },
                onLoginClick = {
                    scope.launch {
                        val loginResult = manager.login(username, password)

                        loginResult.onSuccess {

                            val userResult = manager.getUserInfo()

                            userResult.onSuccess { user ->
                                loggedInUser = user
                                onLoginSuccess(user)
                            }.onFailure {
                                errorText = it.message ?: "Failed to load user"
                            }

                        }.onFailure {
                            errorText = it.message ?: "Login Error"
                        }
                    }
                }
            )

            if (errorText.isNotEmpty()) {
                Spacer16()
                ErrorText(errorText)
            }
        }
    }
}