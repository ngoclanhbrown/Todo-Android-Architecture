package com.brown.todo.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val eventBuffer = Channel<Any>(Channel.BUFFERED)
    val eventFlow = eventBuffer.receiveAsFlow()

    fun postEvent(event: Any) {
        viewModelScope.launch {
            eventBuffer.send(event)
        }
    }

}
