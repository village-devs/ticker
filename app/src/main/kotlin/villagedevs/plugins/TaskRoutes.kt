package villagedevs.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import villagedevs.dto.TaskDTO

fun Route.taskByIdRoute() {
    get("/task/{id}") {
        val id = call.parameters["id"]!!.toLong()
        call.respond(TaskDTO(id))
    }
}