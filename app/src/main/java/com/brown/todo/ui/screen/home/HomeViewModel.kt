package com.brown.todo.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.brown.todo.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val taskList = taskRepository.getTaskListFlow().asLiveData()

}
