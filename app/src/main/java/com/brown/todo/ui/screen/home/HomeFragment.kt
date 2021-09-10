package com.brown.todo.ui.screen.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.brown.todo.R
import com.brown.todo.base.BaseFragment
import com.brown.todo.data.model.Task
import com.brown.todo.data.model.TaskFilter
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
        setHasOptionsMenu(true)
        setUpView()
        setUpObserver()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_action_bar_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_filter -> {
                showFilteringPopUpMenu()
                true
            }
            R.id.item_clear -> {
                viewModel.clearAllTask()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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


    private fun showFilteringPopUpMenu() {
        val view = requireActivity().findViewById<View>(R.id.item_filter)
        PopupMenu(requireContext(), view).apply {
            menuInflater.inflate(R.menu.task_filter_menu, menu)
            setOnMenuItemClickListener {
                viewModel.setFiltering(
                    when (it.itemId) {
                        R.id.item_filter_all -> TaskFilter.ALL
                        R.id.item_filter_active -> TaskFilter.ACTIVE
                        else -> TaskFilter.COMPLETED
                    }
                )
                true
            }
        }.show()
    }

}
