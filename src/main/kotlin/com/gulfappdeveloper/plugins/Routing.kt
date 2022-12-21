package com.gulfappdeveloper.plugins

import com.gulfappdeveloper.dao.DAOFacade
import com.gulfappdeveloper.dao.DAOFacadeImpl

import com.gulfappdeveloper.routes.articleRoutes
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val dao by inject<DAOFacade>()



    install(Routing) {
        articleRoutes(dao = dao)
    }


    routing {

        post("/test") {
            val testSource = call.receive<TestSource>()
            println(testSource)
            call.respond(message = testSource)
        }



        get("/") {
            call.respondText("Hello World!")
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}

@Serializable
data class TestSource(val id:Int,val message:String)


