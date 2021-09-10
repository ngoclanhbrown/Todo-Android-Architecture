package com.brown.todo.ui.screen.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.brown.todo.base.BaseFragment
import com.brown.todo.databinding.FragmentAddTaskBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter

@AndroidEntryPoint
class AddTaskFragment : BaseFragment() {

    private val viewModel: AddTaskViewModel by viewModels()
    private lateinit var binding: FragmentAddTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectEventFlow(viewModel)
        setUpView()
        setUpObserver()
    }


    override fun handleEvent(event: Any) {
        when (val it = event as? AddTaskViewModel.Event) {
            is AddTaskViewModel.Event.Finish -> {
                findNavController().navigateUp()
            }
            is AddTaskViewModel.Event.ShowSnackBar -> {
                showSnackBar(it.message)
            }
            else -> super.handleEvent(event)
        }
    }


    private fun setUpView() {
        binding.apply {
            fabSave.setOnClickListener {
                onSaveTask()
            }

            fabSave.applyInsetter {
                type(navigationBars = true, ime = true) {
                    margin(animated = true)
                }
            }
        }
    }


    private fun setUpObserver() {

    }


    private fun onSaveTask() {
        val title = binding.etTitle.text.toString()
        val description = binding.etDescription.text.toString()
        viewModel.createNewTask(title, description)
    }


    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setAnchorView(binding.fabSave)
            .show()
    }

}
