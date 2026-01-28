package com.example

import com.example.database.DatabaseFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.v1.jdbc.Database

class DataBaseFactoryUnitTest : DatabaseFactory {
    lateinit var source: HikariDataSource

    override fun close() {
        source.close()
    }

    override fun connect() {
        Database.connect(hikari())
        SchemaDefinition.createSchema()
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:mem:unite_test;DATABASE_TO_UPPER=false;MODE=MYSQL"
        config.username = "sa"
        config.password = "secret"
        config.maximumPoolSize = 2
        config.isAutoCommit = true
        config.validate()
        source = HikariDataSource(config)
        return source
    }
}
