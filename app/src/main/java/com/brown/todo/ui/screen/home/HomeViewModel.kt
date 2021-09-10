package com.brown.todo.ui.screen.home

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.brown.todo.base.BaseViewModel
import com.brown.todo.data.TaskRepository
import com.brown.todo.data.model.Task
import com.brown.todo.data.model.TaskFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : BaseViewModel() {

    private val _taskListFlow = taskRepository.getTaskListFlow()
    private val _filterFlow = MutableStateFlow(TaskFilter.ALL)

    val taskList = combine(_taskListFlow, _filterFlow) { tasks, filter ->
        when (filter) {
            TaskFilter.ACTIVE -> tasks.filter { !it.completed }
            TaskFilter.COMPLETED -> tasks.filter { it.completed }
            TaskFilter.ALL -> tasks
        }
    }.asLiveData()


    fun updateStatusForTask(task: Task, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.upsertTask(
                task.copy(completed = isCompleted)
            )
        }
    }


    fun clearAllTask() {
        viewModelScope.launch {
            taskRepository.clearAllCompletedTasks()
        }
    }


    fun setFiltering(filter: TaskFilter) {
        viewModelScope.launch {
            _filterFlow.value = filter
        }
    }

}
