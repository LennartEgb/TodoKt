package dev.lennartegb.mongotodo.repositories

import dev.lennartegb.mongotodo.data.Todo
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val config get() = RealmConfiguration.create(schema = setOf(Todo::class))

class ToDoRepository(private val realm: Realm = Realm.open(config)) {
    suspend fun add(todo: Todo) {
        realm.write { copyToRealm(todo) }
    }

    fun getTodos(): Flow<List<Todo>> {
        return realm.query<Todo>().find().asFlow().map { it.list.toList() }
    }

    suspend fun remove(todo: Todo) {
        realm.write {
            findLatest(todo)?.also { delete(it) }
        }
    }
}