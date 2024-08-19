package dev.lennartegb.data

import dev.lennartegb.data.impl.RealmTodo
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module
import org.koin.dsl.onClose

val dataModule = module {
    single<Realm> {
        Realm.open(RealmConfiguration.create(setOf(RealmTodo::class)))
    } onClose { it?.close() }
    
    factory { ToDoRepository(realm = get()) }
}
