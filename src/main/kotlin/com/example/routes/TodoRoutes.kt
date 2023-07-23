package com.example.routes

import com.example.dao.dao
import com.example.models.CreateTodo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.todoRouting() {
    authenticate("auth0") {
        route("/todos") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("sub").asString()
                call.respond(dao.allTodos(userId))
            }
            get("{id}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("sub").asString()
                val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
                val todo = dao.todo(userId, id)
                if (todo != null) {
                    call.respond(HttpStatusCode.OK, todo)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            post {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("sub").asString()
                val requestTodo = call.receive<CreateTodo>()
                dao.addNewTodo(userId, requestTodo.description!!, requestTodo.title!!)
                call.respond(HttpStatusCode.Created)
            }
            put("{id}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("sub").asString()
                val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
                val todo = call.receive<CreateTodo>()
                dao.editTodo(userId, todo.title!!, todo.description!!, id)
                call.respond(HttpStatusCode.OK)
            }
            delete("{id}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("sub").asString()
                val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
                dao.deleteTodo(userId, id)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
