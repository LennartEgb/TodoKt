package dev.lennartegb.mongotodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.lennartegb.mongotodo.data.Todo
import dev.lennartegb.mongotodo.repositories.ToDoRepository
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: ToDoRepository) : ViewModel() {
    val todos = repository.getTodos().stateIn(viewModelScope, Lazily, emptyList())

    fun add(todo: String) {
        viewModelScope.launch {
            repository.add(Todo().apply { value = todo })
        }
    }

    fun remove(todo: Todo) {
        viewModelScope.launch {
            repository.remove(todo)
        }
    }
}