package codes.whytea.auth

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.util.*

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
        clientId = "614743996912-ectf4r4sj222m10lrmn3lo2pd3c4sjm3.apps.googleusercontent.com",
        clientSecret = System.getenv("GOOGLE_SECRET"),
        defaultScopes = listOf(""),
        authorizeUrlInterceptor = { print(this) }
    )
).associateBy { it.name }

internal fun Application.configureOauth() {

    authentication {
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
            urlProvider = {
                URLBuilder.Companion.createFromCall(this).apply {
                    parameters.clear()
                }.buildString()
            }
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
    }
}