package dev.lennartegb.mongotodo.di

import dev.lennartegb.mongotodo.data.Todo
import dev.lennartegb.mongotodo.repositories.ToDoRepository
import dev.lennartegb.mongotodo.viewmodel.TodoViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.onClose

private val databaseModule = module {
    single<Realm> { Realm.open(RealmConfiguration.create(setOf(Todo::class))) } onClose { it?.close() }
}

private val repositoryModule = module {
    includes(databaseModule)
    factory { ToDoRepository(realm = get()) }
}

private val viewModelModule = module {
    includes(repositoryModule)
    viewModel { TodoViewModel(repository = get()) }
}


val appModule: Module = module {
    includes(viewModelModule)
}