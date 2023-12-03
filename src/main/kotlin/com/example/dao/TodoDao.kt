package com.example.dao

import com.example.models.Todo

interface TodoDao {
    suspend fun allTodos(userId: String): List<Todo>

    suspend fun todo(
        userId: String,
        id: Int,
    ): Todo?

    suspend fun addNewTodo(
        userId: String,
        title: String,
        description: String,
    ): Todo?

    suspend fun editTodo(
        userId: String,
        title: String,
        description: String,
        id: Int,
    ): Boolean

    suspend fun deleteTodo(
        userId: String,
        id: Int,
    ): Boolean
}
