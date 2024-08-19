package dev.lennartegb.mongotodo.di

import dev.lennartegb.data.dataModule
import dev.lennartegb.mongotodo.viewmodel.TodoViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    includes(dataModule)
    viewModel { TodoViewModel(repository = get()) }
}


val appModule: Module = module {
    includes(viewModelModule)
}