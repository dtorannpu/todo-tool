package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.Todo
import com.example.models.Todos
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToTodo(row: ResultRow) = Todo(
        id = row[Todos.id],
        title = row[Todos.title],
        description = row[Todos.description],
    )

    override suspend fun allTodos(userId: String): List<Todo> = dbQuery {
        Todos.select { Todos.userId eq userId }.map(::resultRowToTodo)
    }

    override suspend fun todo(userId: String, id: Int): Todo? = dbQuery {
        Todos
            .select { (Todos.id eq id) and (Todos.userId eq userId) }
            .map(::resultRowToTodo)
            .singleOrNull()
    }

    override suspend fun addNewTodo(userId: String, description: String, title: String): Todo? = dbQuery {
        val insertStatement = Todos.insert {
            it[Todos.title] = title
            it[Todos.description] = description
            it[Todos.userId] = userId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTodo)
    }

    override suspend fun editTodo(userId: String, title: String, description: String, id: Int): Boolean = dbQuery {
        Todos.update({ (Todos.id eq id) and (Todos.userId eq userId) }) {
            it[Todos.title] = title
            it[Todos.description] = description
        } > 0
    }

    override suspend fun deleteTodo(userId: String, id: Int): Boolean = dbQuery {
        Todos.deleteWhere { (Todos.id eq id) and (Todos.userId eq userId) } > 0
    }
}

val dao: DAOFacade = DAOFacadeImpl()