package dev.lennartegb.data

import dev.lennartegb.data.impl.RealmTodo
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class ToDoRepository internal constructor(private val realm: Realm) {
    suspend fun add(todo: Todo) {
        realm.write {
            copyToRealm(
                RealmTodo().apply {
                    value = todo.task
                }
            )
        }
    }

    fun getTodos(): Flow<List<Todo>> = realm.query<RealmTodo>()
        .find()
        .asFlow()
        .map { result -> result.list.map { it.toData() } }

    suspend fun remove(todo: Todo) {
        realm.write {
            query<RealmTodo>("id == $0", todo.id.toObjectId())
                .find().firstOrNull()
                ?.let { findLatest(it) }
                ?.also { delete(it) }
        }
    }

    suspend fun clear() {
        realm.write {
            delete(RealmTodo::class)
        }
    }

    private fun RealmTodo.toData(): Todo = Todo(id = Todo.Id(id.toHexString()), task = value)
    private fun Todo.Id.toObjectId(): ObjectId = ObjectId(hexString = value)
}