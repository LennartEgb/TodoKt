package dev.lennartegb.mongotodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dev.lennartegb.mongotodo.data.Todo
import dev.lennartegb.mongotodo.ui.theme.MongoToDoTheme
import dev.lennartegb.mongotodo.viewmodel.TodoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MongoToDoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val todos by viewModel.todos.collectAsState()
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = innerPadding,
                    ) {
                        input(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            submit = viewModel::add
                        )
                        items(todos) { todo ->
                            TodoItem(todo = todo, onRemove = { viewModel.remove(todo) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TodoItem(todo: Todo, onRemove: () -> Unit, modifier: Modifier = Modifier) {
    ListItem(
        modifier = modifier,
        headlineContent = { Text(text = todo.value) },
        trailingContent = {
            IconButton(onClick = onRemove) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
        }
    )
}

private fun LazyListScope.input(submit: (String) -> Unit, modifier: Modifier = Modifier) = item {
    val (todo, setTodo) = rememberSaveable { mutableStateOf("") }
    TextField(
        modifier = modifier,
        value = todo,
        onValueChange = setTodo,
        keyboardActions = KeyboardActions(onDone = {
            submit(todo)
            setTodo("")
        }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )
}