package com.example.plugins

import com.example.models.CreateTodo
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<CreateTodo> { todo ->
            if (todo.title.isNullOrEmpty())
                ValidationResult.Invalid("titleは必須です。")
            else ValidationResult.Valid

            if (todo.description.isNullOrEmpty())
                ValidationResult.Invalid("descriptionは必須です。")
            else ValidationResult.Valid
        }
    }
}