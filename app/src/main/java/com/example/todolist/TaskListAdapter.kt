package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.Task
import kotlinx.android.synthetic.main.task_view_holder.view.*

class TaskListAdapter(private val onClick: (Task) -> Unit) :
    RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private var taskList = emptyList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(
                R.layout.task_view_holder,
                parent,
                false
            )
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]
        holder.itemView.date_in_holder.text = DateCreator(currentTask.date).parsing
        holder.itemView.name_of_task_view.text = currentTask.name
        holder.itemView.task_view_holder.setOnClickListener {
            val action =
                TaskListFragmentDirections.actionTaskListFragmentToDetailTaskFragment(currentTask)
            holder.itemView.findNavController().navigate(action)
        }
        holder.itemView.delete_task_button.setOnClickListener {
            onClick(currentTask)
        }
    }

    fun setData(task: List<Task>) {
        this.taskList = task
        notifyDataSetChanged()
    }

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
