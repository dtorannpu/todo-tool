package com.example.dao

import com.example.models.Todo

interface DAOFacade {
    suspend fun allTodos(): List<Todo>
    suspend fun todo(id: Int): Todo?
    suspend fun addNewTodo(title: String, description: String): Todo?
    suspend fun editTodo(id: Int, title: String, description: String): Boolean
    suspend fun deleteTodo(id: Int): Boolean
}