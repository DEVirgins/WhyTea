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

val loginProviders = listOf(
    OAuthServerSettings.OAuth2ServerSettings(
        name = "vk",
        authorizeUrl = "https://oauth.vk.com/authorize",
        accessTokenUrl = "https://oauth.vk.com/access_token",
        clientId = "7842898",
        clientSecret = System.getenv("VK_SECRET"),
        defaultScopes = listOf("email"),
        authorizeUrlInterceptor = { print(this) }
    ),
    OAuthServerSettings.OAuth2ServerSettings(
        name = "google",
        authorizeUrl = "https://accounts.google.com/o/oauth2/v2/auth",
        accessTokenUrl = "https://oauth2.googleapis.com/token",
        clientId = "",
        clientSecret = System.getenv("GOOGLE_SECRET"),
        defaultScopes = listOf(""),
        authorizeUrlInterceptor = { print(this) }
    )
).associateBy { it.name }

@Serializable
data class State(val redirectUri: String, val salt: String)

@KtorExperimentalLocationsAPI
fun Application.configureSecurity() {
    val issuer = environment.config.property("jwt.domain").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
        oauth("vk") {
            client = HttpClient(Apache)
            providerLookup = {
                loginProviders["vk"]?.copy(
                    authorizeUrlInterceptor = stateInjector(
                        State(
                            request.uri,
                            generateNonce()
                        )
                    )
                )
            }
            urlProvider = { "/login/vk" }
        }

        oauth("google") {
            client = HttpClient(Apache)
            providerLookup = {
                loginProviders["google"]?.copy(
                    authorizeUrlInterceptor = stateInjector(
                        State(
                            request.uri,
                            generateNonce()
                        )
                    )
                )
            }
            urlProvider = { "/login/google" }
        }

        jwt("auth-jwt") {
            realm = myRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256("secret"))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )
            validate { credential ->
                when {
                    credential.payload.audience.contains(audience) -> JWTPrincipal(credential.payload)
                    else -> null
                }
            }
            challenge { schema, realm -> }
        }
    }
}

@KtorExperimentalLocationsAPI
fun Application.configureSecurityRoutes() {
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

                }
            }
            authenticate("google") {
                get("/google") {

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