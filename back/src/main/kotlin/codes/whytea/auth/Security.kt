package codes.whytea.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import kotlinx.serialization.Serializable

@Serializable
data class State(val redirectUri: String, val salt: String)

@KtorExperimentalLocationsAPI
fun Application.configureSecurity() {
    install(Authentication)
    configureJWT()
    configureOauth()
    configureSecurityRoutes()
}

@KtorExperimentalLocationsAPI
internal fun Application.configureSecurityRoutes() {
    val jwtSettings = this.attributes.get(JWT_SETTINGS)

    routing {
        route("/login") {
            param("error") {
                handle {
                    call.response.status(HttpStatusCode.Unauthorized)
                    call.respond("")
                }
            }

            authenticate("vk") {
                get("/vk") {
                    call.issueJWTByVKId()
                }
            }

            authenticate("google") {
                get("/google") {
                    call.issueJWTByEmail()
                }
            }
        }

        get("/locked") {
            val principal = call.authentication.principal<Principal>()
            call.respondText {
                "Oh hi $principal!"
            }
        }
    }
}