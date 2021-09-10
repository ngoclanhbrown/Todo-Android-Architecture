package com.brown.todo.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brown.todo.data.model.Task
import com.brown.todo.databinding.ItemTaskBinding

class TaskAdapter(
    private val listener: Listener
) : ListAdapter<Task, TaskAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        lateinit var currentTask: Task

        init {
            binding.apply {
                binding.cbComplete.setOnCheckedChangeListener { _, checked ->
                    listener.onChangeCompleteStatus(currentTask, checked)
                }
            }
        }

        fun bind(task: Task) {
            currentTask = task
            binding.apply {
                tvTitle.text = task.title
                cbComplete.isChecked = task.completed

                if (task.completed) {
                    tvTitle.paintFlags = tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    tvTitle.paintFlags = tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }

    }


    interface Listener {
        fun onChangeCompleteStatus(task: Task, isCompleted: Boolean)
    }


    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }

}
