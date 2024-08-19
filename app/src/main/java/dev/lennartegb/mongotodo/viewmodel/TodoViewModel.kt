package dev.lennartegb.mongotodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.lennartegb.data.ToDoRepository
import dev.lennartegb.data.Todo
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: ToDoRepository) :
    ViewModel() {
    val todos = repository.getTodos().stateIn(viewModelScope, Lazily, emptyList())

    fun add(todo: String) {
        viewModelScope.launch {
            repository.add(Todo(task = todo))
        }
    }

    fun remove(todo: Todo) {
        viewModelScope.launch {
            repository.remove(todo)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.clear()
        }
    }
}