package com.gulfappdeveloper.routes

import com.gulfappdeveloper.dao.DAOFacade
import com.gulfappdeveloper.models.Article
import com.gulfappdeveloper.models.Articles
import io.ktor.http.*

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlin.Exception


fun Route.articleRoutes(dao: DAOFacade) {

    get("/getAllArticles") {
        val listOfArticles = dao.allArticles()
        println(listOfArticles)
        call.respond(message = listOfArticles)
    }
    get("/getOneArticle/{id}") {
        try {
            var id: Int = 0
            val idString = call.parameters["id"]
            idString?.let {
                id = it.toInt()
            }
            if (id ==0) {throw Exception("Received value is null")}else {
                println(id)
                var article: com.gulfappdeveloper.models.Article? = null

                article = dao.article(id = id)
                call.respond(mapOf("result" to article))
            }
        } catch (e: Exception) {
            println(e.message)
            call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "There have some error"),
                message = mapOf("result" to e.message)
            )
        }

    }
    post("/insertArticle") {
        try {
            val article = call.receive<Article>()
            val insert = dao.addNewArticle(
                title = article.title,
                body = article.body
            )
            call.respond(mapOf("result" to insert))
        }catch (e:Exception){
            call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Error"),
                message = hashMapOf("result" to e.message)
            )
        }
    }

    put("/editArticle") {
        try {
            val article:Article = call.receive()
            val result = dao.editArticle(
                id = article.id,
                title = article.body,
                body = article.body
            )
            call.respond(message = result)
        }catch (e:Exception){
            println(e.message)
            call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Error"),
                message = hashMapOf("result" to e.message)
            )
        }

    }

    delete("/deleteOneArticle/{id}") {
        try {
            var id: Int = 0
            val idString = call.parameters["id"]
            idString?.let {
                id = it.toInt()
            }
            if (id<=1) throw Exception("Bad request id lees than 0")
            val result = dao.deleteArticle(id = id)
            call.respond(message = hashMapOf("result" to result))

        }catch (e:Exception){
            println(e.message)
            call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Error"),
                message = hashMapOf("result" to e.message)
            )
        }
    }


}

