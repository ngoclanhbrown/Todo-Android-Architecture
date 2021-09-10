package com.brown.todo.ui.screen.home

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.brown.todo.base.BaseViewModel
import com.brown.todo.data.TaskRepository
import com.brown.todo.data.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : BaseViewModel() {

    val taskList = taskRepository.getTaskListFlow().asLiveData()

    fun updateStatusForTask(task: Task, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.upsertTask(
                task.copy(completed = isCompleted)
            )
        }
    }

}
