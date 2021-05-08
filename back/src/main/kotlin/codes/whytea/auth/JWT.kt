package codes.whytea.auth

import codes.whytea.model.User
import codes.whytea.model.Users
import codes.whytea.persistence.query
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.MessageDigest
import java.util.*

val JWT_VERIFIER = AttributeKey<JWTVerifier>("JWT_VERIFIER")
val JWT_SETTINGS = AttributeKey<JWTSettings>("JWT_SETTINGS")

internal fun Application.configureJWT(){
    val issuer = environment.config.property("jwt.domain").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val secret = environment.config.property("jwt.secret").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    val algorithm = Algorithm.HMAC256(secret)

    val jwtVerifier = JWT.require(algorithm)
                         .withAudience(audience)
                         .withIssuer(issuer)
                         .build()

    this.attributes.put(JWT_VERIFIER, jwtVerifier)

    this.attributes.put(JWT_SETTINGS, JWTSettings(issuer, audience, algorithm))

    this.authentication {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(jwtVerifier)
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

data class JWTSettings(val issuer: String, val audience: String, val algorithm: Algorithm)


suspend fun ApplicationCall.issueJWTByVKId(): String = query {
    val oauthData = principal<OAuthAccessTokenResponse.OAuth2>()!!
    transaction {
        val user =
            User.find {
                Users.vkUserId eq oauthData.extraParameters["user_id"]!!.toInt()
            }.firstOrNull()
                ?:
            User.new{
                name = oauthData.extraParameters["name"]!!
                email = oauthData.extraParameters["email"]!!
                vkId = oauthData.extraParameters["user_id"]!!.toInt()
            }
        issueJWT(user)
    }
}

suspend fun ApplicationCall.issueJWTByEmail(): String = query {
    val oauthData = principal<OAuthAccessTokenResponse.OAuth2>()!!
    transaction {
        val user =
            User.find {
                Users.email eq oauthData.extraParameters["email"]!!
            }.firstOrNull()
                ?:
                User.new{
                    name = oauthData.extraParameters["name"]!!
                    email = oauthData.extraParameters["email"]!!
                }
        issueJWT(user)
    }
}

fun ApplicationCall.issueJWT(user: User): String {
    val jwtSettings = attributes[JWT_SETTINGS]
    return JWT.create()
              .withJWTId(
                  MessageDigest.getInstance("SHA-256")
                               .digest((Date().toString() + user.name).toByteArray())
                                .fold("", { str, it -> str + "%02x".format(it) }))
              .withIssuer(jwtSettings.issuer)
              .withAudience(jwtSettings.audience)
              .sign(jwtSettings.algorithm)
}
