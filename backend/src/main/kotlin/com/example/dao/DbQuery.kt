package com.example.dao

import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

object DbQuery {
    suspend fun <T> dbQuery(block: suspend () -> T): T = suspendTransaction { block() }
}
