package codes.whytea

import codes.whytea.auth.configureSecurity
import codes.whytea.auth.configureSecurityRoutes
import codes.whytea.model.Skills
import codes.whytea.model.Users
import codes.whytea.persistence.connectToDatabase
import codes.whytea.persistence.query
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.serialization.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
fun Application.main() {
    val db = connectToDatabase()
    Database.connect(db)
    install(Locations)
    runBlocking {
        query {
            SchemaUtils.create(Users, Skills)
        }
//        configureRouting()
        configureSecurity()
        configureSecurityRoutes()
        configureSerialization()
    }

}


fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}
