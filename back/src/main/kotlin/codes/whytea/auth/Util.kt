package codes.whytea.auth

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

fun OAuthServerSettings.OAuth2ServerSettings.copy(
    name: String                                          = this.name,
    authorizeUrl: String                                  = this.authorizeUrl,
    accessTokenUrl: String                                = this.accessTokenUrl,
    requestMethod: HttpMethod                             = this.requestMethod,
    clientId: String                                      = this.clientId,
    clientSecret: String                                  = this.clientSecret,
    defaultScopes: List<String>                           = this.defaultScopes,
    accessTokenRequiresBasicAuth: Boolean                 = this.accessTokenRequiresBasicAuth,
    nonceManager: NonceManager                            = this.nonceManager,
    authorizeUrlInterceptor: URLBuilder.() -> Unit        = this.authorizeUrlInterceptor,
    passParamsInURL: Boolean                              = this.passParamsInURL,
    accessTokenInterceptor: HttpRequestBuilder.() -> Unit = this.accessTokenInterceptor,
) = OAuthServerSettings.OAuth2ServerSettings(
        name,
        authorizeUrl,
        accessTokenUrl,
        requestMethod,
        clientId,
        clientSecret,
        defaultScopes,
        accessTokenRequiresBasicAuth,
        nonceManager,
        authorizeUrlInterceptor,
        passParamsInURL,
        accessTokenInterceptor)

fun ApplicationCall.stateInjector(data: State): URLBuilder.()->Unit = {
   this.parameters["state"] = Base64.getEncoder().encodeToString(Json.encodeToString(data).toByteArray())
}