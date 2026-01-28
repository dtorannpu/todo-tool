package com.example

import com.example.models.Todos
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object SchemaDefinition {
    fun createSchema() {
        transaction {
            SchemaUtils.create(Todos)
        }
    }
}
