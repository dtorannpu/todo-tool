package com.example.plugins

import com.example.models.CreateTodo
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<CreateTodo> { todo ->
            if (todo.title.isNullOrEmpty()) {
                ValidationResult.Invalid("titleは必須です。")
            } else {
                ValidationResult.Valid
            }

            if (todo.description.isNullOrEmpty()) {
                ValidationResult.Invalid("descriptionは必須です。")
            } else {
                ValidationResult.Valid
            }
        }
    }
}
