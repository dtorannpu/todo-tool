package com.example.routes

import com.example.dao.TodoDao
import com.example.models.CreateTodo
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.todoRouting() {
    val dao by inject<TodoDao>()
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
                dao.addNewTodo(userId, requestTodo.title!!, requestTodo.description!!)
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
