package codes.whytea.plugins

import io.ktor.locations.*

@Location("/location/{name}") @KtorExperimentalLocationsAPI
class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")


@Location("/type/{name}") @KtorExperimentalLocationsAPI
data class Type(val name: String) {
    @Location("/edit")
    data class Edit(val type: Type)

    @Location("/list/{page}")
    data class List(val type: Type, val page: Int)
}
