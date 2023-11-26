package com.example

import com.ninja_squad.dbsetup.Operations.deleteAllFrom
import com.ninja_squad.dbsetup.operation.DeleteAll
import com.ninja_squad.dbsetup_kotlin.insertInto
import com.ninja_squad.dbsetup_kotlin.mappedValues

object TodosOperation {
    val TODOS_DELETE: DeleteAll = deleteAllFrom("todos")
    val TODOS_INSERT = insertInto("todos") {
        mappedValues("id" to 1, "title" to "title1", "description" to "description1", "user_id" to "test")
        mappedValues("id" to 2, "title" to "title2", "description" to "description2", "user_id" to "other")
        mappedValues("id" to 3, "title" to "title3", "description" to "description3", "user_id" to "test")
        mappedValues("id" to 5, "title" to "x", "description" to "y", "user_id" to "test")
        mappedValues("id" to 100, "title" to "titleDelete", "description" to "descriptionDelete", "user_id" to "test")
    }
}