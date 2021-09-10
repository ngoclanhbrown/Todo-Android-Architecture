package com.brown.todo.ui.screen.add

import androidx.lifecycle.viewModelScope
import com.brown.todo.R
import com.brown.todo.TodoApp
import com.brown.todo.base.BaseViewModel
import com.brown.todo.data.TaskRepository
import com.brown.todo.data.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val app: TodoApp,
    private val taskRepository: TaskRepository
) : BaseViewModel() {

    fun createNewTask(title: String, description: String) {
        if (title.isBlank()) {
            val message = app.getString(R.string.title_empty_warning)
            postEvent(Event.ShowSnackBar(message))
            return
        }

        viewModelScope.launch {
            taskRepository.upsertTask(Task(title, description))
            postEvent(Event.Finish)
        }
    }


    sealed class Event {
        object Finish : Event()
        data class ShowSnackBar(val message: String) : Event()
    }

}
