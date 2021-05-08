package codes.whytea.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.get

@KtorExperimentalLocationsAPI
fun Application.configureRouting() {
    install(Locations)

    routing {
        static("/") {
            files("css")
            default("index.html")
        }
    }

}

@Location("/location/{name}") @KtorExperimentalLocationsAPI
class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")


@Location("/type/{name}") @KtorExperimentalLocationsAPI
data class Type(val name: String) {
    @Location("/edit")
    data class Edit(val type: Type)

    @Location("/list/{page}")
    data class List(val type: Type, val page: Int)
}
