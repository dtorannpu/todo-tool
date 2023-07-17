package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Todo(val id: Int, val title: String, val description:String)
@Serializable
data class CreateTodo(val title: String?, val description: String?)

object Todos : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val description = varchar("description", 1024)

    override val primaryKey = PrimaryKey(id)
}