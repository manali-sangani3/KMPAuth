import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(private val client: HttpClient) {


    suspend fun login(username: String, password: String): UserResponse {
        return client.post("https://dummyjson.com/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(username, password))
        }.body()
    }

    suspend fun userinfo(): UserResponse {
        return client.get("https://dummyjson.com/auth/me") {
            header("Authorization", "Bearer ${SessionManager.accessToken}")
            contentType(ContentType.Application.Json)
        }.body()
    }
}