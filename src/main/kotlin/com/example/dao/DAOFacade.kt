package com.example.dao

import com.example.models.Todo

interface DAOFacade {
    suspend fun allTodos(userId: String): List<Todo>
    suspend fun todo(userId: String, id: Int): Todo?
    suspend fun addNewTodo(userId: String, description: String, title: String): Todo?
    suspend fun editTodo(userId: String, title: String, description: String, id: Int): Boolean
    suspend fun deleteTodo(userId: String, id: Int): Boolean
}