package com.example.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.TodosOperation
import com.example.appTestModule
import com.example.config.AppConfig
import com.example.config.setupConfig
import com.example.database.DatabaseFactory
import com.example.models.CreateTodo
import com.example.plugins.*
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.Operations.sequenceOf
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import org.assertj.db.api.Assertions.assertThat
import org.assertj.db.type.Changes
import org.assertj.db.type.Source
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TodoRoutesKtTest {
    private lateinit var token: String
    private lateinit var changes: Changes

    @BeforeTest
    fun setup() {
        token = JWT.create()
            .withClaim("sub", "test")
            .sign(Algorithm.none())
    }

    @Test
    fun testGetAllTodo() = testApplication {
        application {
            install(Koin) {
                slf4jLogger()
                modules(appTestModule)
            }
            setupConfig()
            val databaseFactory by inject<DatabaseFactory>()
            databaseFactory.connect()
            configureJWTTest()
            configureSerialization()
            configureValidation()
            configStatusPages()
            configureRouting()
            val appConfig by inject<AppConfig>()
            val dbConfig = appConfig.databaseConfig
            val dbSetup =
                DbSetup(
                    DriverManagerDestination(dbConfig.jdbcUrl, dbConfig.username, dbConfig.password),
                    sequenceOf(TodosOperation.TODOS_DELETE, TodosOperation.TODOS_INSERT)
                )
            dbSetup.launch()
        }
        application {

        }
        client.get("/todos") {
            headers {
                append("Authorization", "Bearer $token")
            }
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), contentType())
            val expectedJson = """
                [
                  {"id": 1, "title":"title1", "description": "description1"},
                  {"id": 3, "title":"title3", "description": "description3"},
                  {"id": 5, "title":"x", "description": "y"},
                  {"id": 100, "title":"titleDelete", "description": "descriptionDelete"}
                ]
            """.trimIndent()
            JSONAssert.assertEquals(expectedJson, bodyAsText(), true)
        }
    }

    @Test
    fun testGetTodo() = testApplication {
        application {
            install(Koin) {
                slf4jLogger()
                modules(appTestModule)
            }
            setupConfig()
            val databaseFactory by inject<DatabaseFactory>()
            databaseFactory.connect()
            configureJWTTest()
            configureSerialization()
            configureValidation()
            configStatusPages()
            configureRouting()
            val appConfig by inject<AppConfig>()
            val dbConfig = appConfig.databaseConfig
            val dbSetup =
                DbSetup(
                    DriverManagerDestination(dbConfig.jdbcUrl, dbConfig.username, dbConfig.password),
                    sequenceOf(TodosOperation.TODOS_DELETE, TodosOperation.TODOS_INSERT)
                )
            dbSetup.launch()
        }

        client.get("/todos/1") {
            headers {
                append("Authorization", "Bearer $token")
            }
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), contentType())
            val expectedJson = """{"id": 1, "title":"title1", "description": "description1"}"""
            JSONAssert.assertEquals(expectedJson, bodyAsText(), true)
        }
    }


    @Test
    fun testGetTodoNotFound() = testApplication {
        application {
            install(Koin) {
                slf4jLogger()
                modules(appTestModule)
            }
            setupConfig()
            val databaseFactory by inject<DatabaseFactory>()
            databaseFactory.connect()
            configureJWTTest()
            configureSerialization()
            configureValidation()
            configStatusPages()
            configureRouting()
            val appConfig by inject<AppConfig>()
            val dbConfig = appConfig.databaseConfig
            val dbSetup =
                DbSetup(
                    DriverManagerDestination(dbConfig.jdbcUrl, dbConfig.username, dbConfig.password),
                    sequenceOf(TodosOperation.TODOS_DELETE, TodosOperation.TODOS_INSERT)
                )
            dbSetup.launch()
        }

        client.get("/todos/200") {
            headers {
                append("Authorization", "Bearer $token")
            }
        }.apply {
            assertEquals(HttpStatusCode.NotFound, status)
        }
    }

    @Test
    fun testCreateTodo() = testApplication {
        application {
            install(Koin) {
                slf4jLogger()
                modules(appTestModule)
            }
            setupConfig()
            val databaseFactory by inject<DatabaseFactory>()
            databaseFactory.connect()
            configureJWTTest()
            configureSerialization()
            configureValidation()
            configStatusPages()
            configureRouting()
            val appConfig by inject<AppConfig>()
            val dbConfig = appConfig.databaseConfig
            val dbSetup =
                DbSetup(
                    DriverManagerDestination(dbConfig.jdbcUrl, dbConfig.username, dbConfig.password),
                    sequenceOf(TodosOperation.TODOS_DELETE)
                )
            dbSetup.launch()
            changes = Changes(Source(dbConfig.jdbcUrl, dbConfig.username, dbConfig.password))
            changes.setStartPointNow()
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        client.post("/todos") {
            headers {
                append("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
            }
            setBody(CreateTodo("title", "description"))
        }.apply {
            changes.setEndPointNow()
            assertEquals(HttpStatusCode.Created, status)
            assertThat(changes)
                .hasNumberOfChanges(1)
                .ofCreationOnTable("todos")
                .hasNumberOfChanges(1)
                .changeOnTable("todos")
                .isCreation()
                .rowAtEndPoint()
                .value("title").isEqualTo("title")
                .value("description").isEqualTo("description")
                .value("user_id").isEqualTo("test")
        }
    }

    @Test
    fun testUpdateTodo() = testApplication {
        application {
            install(Koin) {
                slf4jLogger()
                modules(appTestModule)
            }
            setupConfig()
            val databaseFactory by inject<DatabaseFactory>()
            databaseFactory.connect()
            configureJWTTest()
            configureSerialization()
            configureValidation()
            configStatusPages()
            configureRouting()
            val appConfig by inject<AppConfig>()
            val dbConfig = appConfig.databaseConfig
            val dbSetup =
                DbSetup(
                    DriverManagerDestination(dbConfig.jdbcUrl, dbConfig.username, dbConfig.password),
                    sequenceOf(TodosOperation.TODOS_DELETE, TodosOperation.TODOS_INSERT)
                )
            dbSetup.launch()
            changes = Changes(Source(dbConfig.jdbcUrl, dbConfig.username, dbConfig.password))
            changes.setStartPointNow()
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        client.put("/todos/5") {
            headers {
                append("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
            }
            setBody(CreateTodo("updateTitle", "updateDescription"))
        }.apply {
            changes.setEndPointNow()
            assertEquals(HttpStatusCode.OK, status)
            assertThat(changes)
                .hasNumberOfChanges(1)
                .ofModificationOnTable("todos")
                .hasNumberOfChanges(1)
                .changeOnTable("todos")
                .isModification()
                .rowAtEndPoint()
                .value("id").isEqualTo(5)
                .value("title").isEqualTo("updateTitle")
                .value("description").isEqualTo("updateDescription")
        }
    }

    @Test
    fun testDeleteTodo() = testApplication {
        application {
            install(Koin) {
                slf4jLogger()
                modules(appTestModule)
            }
            setupConfig()
            val databaseFactory by inject<DatabaseFactory>()
            databaseFactory.connect()
            configureJWTTest()
            configureSerialization()
            configureValidation()
            configStatusPages()
            configureRouting()
            val appConfig by inject<AppConfig>()
            val dbConfig = appConfig.databaseConfig
            val dbSetup =
                DbSetup(
                    DriverManagerDestination(dbConfig.jdbcUrl, dbConfig.username, dbConfig.password),
                    sequenceOf(TodosOperation.TODOS_DELETE, TodosOperation.TODOS_INSERT)
                )
            dbSetup.launch()
            changes = Changes(Source(dbConfig.jdbcUrl, dbConfig.username, dbConfig.password))
            changes.setStartPointNow()
        }

        client.delete("/todos/100") {
            headers {
                append("Authorization", "Bearer $token")
            }
        }.apply {
            changes.setEndPointNow()
            assertEquals(HttpStatusCode.OK, status)
            assertThat(changes)
                .hasNumberOfChanges(1)
                .ofDeletionOnTable("todos")
                .hasNumberOfChanges(1)
                .changeOnTable("todos")
                .isDeletion()
                .rowAtStartPoint()
                .value("id").isEqualTo(100)
        }
    }
}