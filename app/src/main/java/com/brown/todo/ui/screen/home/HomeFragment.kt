package com.brown.todo.ui.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.brown.todo.base.BaseFragment
import com.brown.todo.data.model.Task
import com.brown.todo.databinding.FragmentHomeBinding
import com.brown.todo.ui.adapter.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    private val adapter = TaskAdapter(
        object : TaskAdapter.Listener {
            override fun onChangeCompleteStatus(task: Task, isCompleted: Boolean) {
                viewModel.updateStatusForTask(task, isCompleted)
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectEventFlow(viewModel)
        setUpView()
        setUpObserver()
    }


    private fun setUpView() {
        binding.apply {
            rvTask.adapter = adapter
            fabAdd.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionAddNewTask())
            }

            fabAdd.applyInsetter {
                type(navigationBars = true) {
                    margin()
                }
            }
        }
    }


    private fun setUpObserver() {
        viewModel.taskList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}
