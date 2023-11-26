package com.example.dao

import com.example.DataBaseFactoryUnitTest
import com.example.TodosOperation
import com.example.models.Todo
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.Operations.sequenceOf
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import kotlinx.coroutines.test.runTest
import org.assertj.db.api.Assertions
import org.assertj.db.type.Changes
import kotlin.test.*

class TodoDaoImplTest {
    private lateinit var databaseFactory: DataBaseFactoryUnitTest
    private lateinit var todoDaoImpl: TodoDaoImpl

    @BeforeTest
    fun setup() {
        databaseFactory = DataBaseFactoryUnitTest()
        databaseFactory.connect()
        todoDaoImpl = TodoDaoImpl()
    }

    @AfterTest
    fun tearDown() {
        databaseFactory.close()
    }

    @Test
    fun testAllTodos() = runTest {
        val dbSetup = DbSetup(
            DataSourceDestination(databaseFactory.source),
            sequenceOf(TodosOperation.TODOS_DELETE, TodosOperation.TODOS_INSERT)
        )
        dbSetup.launch()

        val result = todoDaoImpl.allTodos("test")

        assertContentEquals(
            listOf(
                Todo(1, "title1", "description1"),
                Todo(3, "title3", "description3"),
                Todo(5, "x", "y"),
                Todo(100, "titleDelete", "descriptionDelete")
            ), result
        )
    }


    @Test
    fun testTodoExists() = runTest {
        val dbSetup = DbSetup(
            DataSourceDestination(databaseFactory.source),
            sequenceOf(TodosOperation.TODOS_DELETE, TodosOperation.TODOS_INSERT)
        )
        dbSetup.launch()

        val result = todoDaoImpl.todo("test", 1)

        assertEquals(Todo(1, "title1", "description1"), result)
    }


    @Test
    fun testTodoNotExists() = runTest {
        val dbSetup = DbSetup(
            DataSourceDestination(databaseFactory.source),
            sequenceOf(TodosOperation.TODOS_DELETE, TodosOperation.TODOS_INSERT)
        )
        dbSetup.launch()

        val result = todoDaoImpl.todo("test", 9)

        assertNull(result)
    }

    @Test
    fun testAddNewTodo() = runTest {
        val changes = Changes(databaseFactory.source)
        changes.setStartPointNow()
        val dbSetup = DbSetup(
            DataSourceDestination(databaseFactory.source),
            sequenceOf(TodosOperation.TODOS_DELETE)
        )
        dbSetup.launch()

        changes.setStartPointNow()
        val result = todoDaoImpl.addNewTodo("test", "title1", "description1")
        changes.setEndPointNow()

        assertEquals("title1", result!!.title)
        assertEquals("description1", result.description)
        Assertions.assertThat(changes)
            .hasNumberOfChanges(1)
            .ofCreationOnTable("todos")
            .hasNumberOfChanges(1)
            .changeOnTable("todos")
            .isCreation()
            .rowAtEndPoint()
            .value("title").isEqualTo("title1")
            .value("description").isEqualTo("description1")
            .value("user_id").isEqualTo("test")
    }

    @Test
    fun testEditTodoOk() = runTest {
        val changes = Changes(databaseFactory.source)
        changes.setStartPointNow()
        val dbSetup = DbSetup(
            DataSourceDestination(databaseFactory.source),
            sequenceOf(TodosOperation.TODOS_DELETE, TodosOperation.TODOS_INSERT)
        )
        dbSetup.launch()

        changes.setStartPointNow()
        val result = todoDaoImpl.editTodo("test", "title1change", "description1change", 1)
        changes.setEndPointNow()

        assertTrue(result)
        Assertions.assertThat(changes)
            .hasNumberOfChanges(1)
            .ofModificationOnTable("todos")
            .hasNumberOfChanges(1)
            .changeOnTable("todos")
            .isModification()
            .rowAtEndPoint()
            .value("id").isEqualTo(1)
            .value("title").isEqualTo("title1change")
            .value("description").isEqualTo("description1change")
    }

    @Test
    fun testEditTodoNg() = runTest {
        val changes = Changes(databaseFactory.source)
        changes.setStartPointNow()
        val dbSetup = DbSetup(
            DataSourceDestination(databaseFactory.source),
            sequenceOf(TodosOperation.TODOS_DELETE, TodosOperation.TODOS_INSERT)
        )
        dbSetup.launch()

        changes.setStartPointNow()
        val result = todoDaoImpl.editTodo("xxxx", "title1change", "description1change", 1)
        changes.setEndPointNow()

        assertFalse(result)
        Assertions.assertThat(changes)
            .hasNumberOfChanges(0)
    }

    @Test
    fun testDeleteTodoOk() = runTest {
        val changes = Changes(databaseFactory.source)
        changes.setStartPointNow()
        val dbSetup = DbSetup(
            DataSourceDestination(databaseFactory.source),
            sequenceOf(TodosOperation.TODOS_DELETE, TodosOperation.TODOS_INSERT)
        )
        dbSetup.launch()

        changes.setStartPointNow()
        val result = todoDaoImpl.deleteTodo("test", 1)
        changes.setEndPointNow()

        assertTrue(result)
        Assertions.assertThat(changes)
            .hasNumberOfChanges(1)
            .ofDeletionOnTable("todos")
            .hasNumberOfChanges(1)
            .changeOnTable("todos")
            .isDeletion()
            .rowAtStartPoint()
            .value("id").isEqualTo(1)
    }

    @Test
    fun testDeleteTodoNg() = runTest {
        val changes = Changes(databaseFactory.source)
        changes.setStartPointNow()
        val dbSetup = DbSetup(
            DataSourceDestination(databaseFactory.source),
            sequenceOf(TodosOperation.TODOS_DELETE, TodosOperation.TODOS_INSERT)
        )
        dbSetup.launch()

        changes.setStartPointNow()
        val result = todoDaoImpl.deleteTodo("xxxx", 1)
        changes.setEndPointNow()

        assertFalse(result)
        Assertions.assertThat(changes)
            .hasNumberOfChanges(0)
    }
}