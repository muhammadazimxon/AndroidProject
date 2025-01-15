package com.example.todoproject.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoproject.components.bodyContainer.BodyContainer
import com.example.todoproject.components.upperContainer.UpperContainer
import com.example.todoproject.components.taskContainer.Urgency
import com.example.todoproject.components.taskContainer.TaskContainer
import java.util.logging.Filter

enum class FilterType { ALL, ACTIVE, COMPLETED }

sealed class TaskEvent() {
    data class AddOnClick(val text: String, val importance: Urgency) : TaskEvent()
    data class HandleOnChoose(val text: String) : TaskEvent()
    data class InputOnEnter(val text: String) : TaskEvent()
    data class DeleteTask(val id: Int) : TaskEvent()
    data class CheckBoxOnChange(val id: Int, val isSelected: Boolean) : TaskEvent()
    data class FilterOnChoose(val filterType: FilterType) : TaskEvent()
    data class OnEdit(val id: Int) : TaskEvent()
}
data class TaskComponents(
    var filter: FilterType = FilterType.ALL,
    var input: String = "",
    val listOfUrgency: List<Urgency> = listOf(Urgency.PALTRY, Urgency.NORMAL, Urgency.URGENT),
    var isAddOnClick: Boolean = true,
    var importance: String = "PALTRY"
)

class TaskViewModel : ViewModel() {
    private var _tasks by mutableStateOf<List<Task>>(emptyList())
    var taskComponents by mutableStateOf(TaskComponents())

    private var _idCounter = 0
    private fun generateID() = _idCounter++
    private var idOfEdit = 0
    val filteredTasks: List<Task>
        get() = when (taskComponents.filter) {
            FilterType.ALL -> _tasks
            FilterType.ACTIVE -> _tasks.filter { !it.isSelected }
            FilterType.COMPLETED -> _tasks.filter { it.isSelected }
        }
    fun eventHandler(event: TaskEvent) {
        when(event) {
            is TaskEvent.AddOnClick -> {
                if (event.text.isNotBlank()) {
                    _tasks = if (taskComponents.isAddOnClick) {
                        listOf(
                            Task(
                                text = event.text.trim(),
                                urgency = event.importance,
                                id = generateID())
                        ) + _tasks
                    } else {
                        _tasks.map {
                            if (it.id == idOfEdit)
                                it.copy(
                                    text = taskComponents.input,
                                    urgency = event.importance
                                )
                            else
                                it
                        }
                    }
                    taskComponents = taskComponents.copy(isAddOnClick = true)
                    taskComponents = taskComponents.copy(input = "")
                }
            }
            is TaskEvent.HandleOnChoose -> {
                taskComponents = taskComponents.copy(importance = event.text)
            }
            is TaskEvent.InputOnEnter -> {
                taskComponents = taskComponents.copy(input = event.text)
            }
            is TaskEvent.DeleteTask -> {
                _tasks = _tasks.filterNot { it.id == event.id }
            }
            is TaskEvent.CheckBoxOnChange -> {
                _tasks = _tasks.map { task ->
                    if (task.id == event.id) task.copy(isSelected = event.isSelected)
                    else task
                }
            }
            is TaskEvent.FilterOnChoose -> {
                taskComponents = taskComponents.copy(filter = event.filterType)
            }
            is TaskEvent.OnEdit -> {
                _tasks.forEach {
                    if(it.id == event.id) {
                        taskComponents = taskComponents.copy(input = it.text)
                        taskComponents = taskComponents.copy(isAddOnClick = false)
                        idOfEdit = event.id
                    }
                }
            }
        }
    }
}

@Composable
fun App(viewModel: TaskViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp)
            .padding(10.dp)
    ) {
        UpperContainer(
            isAddOnClick = viewModel.taskComponents.isAddOnClick,
            inputOnTextField = viewModel.taskComponents.input,
            importance = viewModel.taskComponents.importance,
            listOfUrgency = viewModel.taskComponents.listOfUrgency,
            handlerOnChoose = { it: String -> viewModel.eventHandler(TaskEvent.HandleOnChoose(it)) },
            inputOnEnter = { viewModel.eventHandler(TaskEvent.InputOnEnter(it)) },
            addOnClick = { viewModel.eventHandler(TaskEvent.AddOnClick(viewModel.taskComponents.input, it)) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        BodyContainer(filterTask = viewModel.taskComponents.filter, onClickChange = { viewModel.eventHandler(TaskEvent.FilterOnChoose(it)) })
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(5.dp),
            reverseLayout = false
        ) {
            items(viewModel.filteredTasks) { task ->
                TaskContainer(
                    task = task,
                    onDelete = { viewModel.eventHandler(TaskEvent.DeleteTask(it)) },
                    checkBoxOnChange = { isSelected: Boolean, id: Int ->
                        viewModel.eventHandler(TaskEvent.CheckBoxOnChange(id = id, isSelected = isSelected))
                    },
                    onEdit = { viewModel.eventHandler(TaskEvent.OnEdit(it)) }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp", showSystemUi = true)
@Composable
fun ApplicationPreview() {
    App()
}