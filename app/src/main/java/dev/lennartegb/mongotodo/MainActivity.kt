package dev.lennartegb.mongotodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dev.lennartegb.mongotodo.data.Todo
import dev.lennartegb.mongotodo.ui.theme.MongoToDoTheme
import dev.lennartegb.mongotodo.viewmodel.TodoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MongoToDoTheme {
                val viewModel by viewModel<TodoViewModel>()
                val todos by viewModel.todos.collectAsState()
                TodoScaffold(
                    modifier = Modifier.fillMaxSize(),
                    todos = todos,
                    onRemove = viewModel::remove,
                    onDeleteAll = viewModel::deleteAll,
                    onAdd = viewModel::add,
                )
            }
        }
    }
}

@Composable
private fun TodoScaffold(
    todos: List<Todo>,
    onRemove: (Todo) -> Unit,
    onDeleteAll: () -> Unit,
    onAdd: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = onDeleteAll) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    }
                },
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            stickyHeader(key = "input") {
                TodoInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    submit = onAdd,
                )
            }
            items(todos) { todo ->
                TodoItem(todo = todo, onRemove = { onRemove(todo) })
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

@Composable
private fun TodoInput(
    submit: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (todo, setTodo) = rememberSaveable { mutableStateOf("") }
    TextField(
        modifier = modifier,
        value = todo,
        onValueChange = setTodo,
        singleLine = true,
        label = { Text("Add your todo") },
        keyboardActions = KeyboardActions(
            onDone = {
                submit(todo)
                setTodo("")
            }
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )
}
