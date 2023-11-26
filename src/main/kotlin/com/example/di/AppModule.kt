package com.example.di

import com.example.config.AppConfig
import com.example.dao.TodoDao
import com.example.dao.TodoDaoImpl
import com.example.database.DatabaseFactory
import com.example.database.DatabaseFactoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::AppConfig)
    factory<DatabaseFactory> { DatabaseFactoryImpl(get()) }
    factory<TodoDao> { TodoDaoImpl() }
}