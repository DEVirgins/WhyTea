package codes.whytea.integrations

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

val client = HttpClient(Apache){
    install(JsonFeature)
}

suspend fun getUserInfo(userId: Int, accessToken: String) = client.get<UserInfoResponse>("https://api.vk.com/method/users.get?v=5.52") {
    parameter("user_id", userId)
    parameter("access_token", accessToken)
}.response.first()

@Serializable
data class UserInfoResponse(val response: List<UserInfo>)

@Serializable
data class UserInfo(
    @SerialName("first_name") val firstName: String,
    val id: Long,
    @SerialName("last_name")  val lastName: String)