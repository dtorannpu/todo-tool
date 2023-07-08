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

    override suspend fun allTodos(): List<Todo> = dbQuery {
        Todos.selectAll().map(::resultRowToTodo)
    }

    override suspend fun todo(id: Int): Todo? = dbQuery {
        Todos
            .select { Todos.id eq id }
            .map(::resultRowToTodo)
            .singleOrNull()
    }

    override suspend fun addNewTodo(title: String, description: String): Todo? = dbQuery {
        val insertStatement = Todos.insert {
            it[Todos.title] = title
            it[Todos.description] = description
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTodo)
    }

    override suspend fun editTodo(id: Int, title: String, description: String): Boolean = dbQuery {
        Todos.update({ Todos.id eq id }) {
            it[Todos.title] = title
            it[Todos.description] = description
        } > 0
    }

    override suspend fun deleteTodo(id: Int): Boolean = dbQuery {
        Todos.deleteWhere { Todos.id eq id } > 0
    }
}

val dao: DAOFacade = DAOFacadeImpl()