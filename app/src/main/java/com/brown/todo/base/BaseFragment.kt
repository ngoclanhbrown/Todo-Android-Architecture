package com.brown.todo.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

open class BaseFragment : Fragment() {

    fun <VM : BaseViewModel> collectEventFlow(viewModel: VM) {
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
    }

    open fun handleEvent(event: Any) {}

}
