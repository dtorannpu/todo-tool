package com.example.database

import com.example.config.AppConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.v1.jdbc.Database

class DatabaseFactoryImpl(
    appConfig: AppConfig,
) : DatabaseFactory {
    private val dbConfig = appConfig.databaseConfig

    override fun connect() {
        Database.connect(hikari())
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = dbConfig.driverClassName
        config.jdbcUrl = dbConfig.jdbcUrl
        config.username = dbConfig.username
        config.password = dbConfig.password
        config.maximumPoolSize = dbConfig.maximumPoolSize
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_READ_COMMITTED"
        config.validate()
        return HikariDataSource(config)
    }

    override fun close() {
    }
}
