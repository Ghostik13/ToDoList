package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.Task
import kotlinx.android.synthetic.main.task_view_holder.view.*

class TaskListAdapter(
    private val onClickDelete: (Task) -> Unit,
    private val onClickUpdate: (Task) -> Unit,
    private val onClickDone: (Task) -> Unit
) :
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
        initCheckBox(currentTask, holder)
        holder.itemView.delete_task_button.setOnClickListener {
            onClickDelete(currentTask)
        }
        holder.itemView.task_view_holder.setOnClickListener {
            onClickUpdate(currentTask)
        }
    }

    private fun initCheckBox(
        currentTask: Task,
        holder: TaskViewHolder
    ) {
        if (currentTask.done) {
            holder.itemView.check_box.isChecked = true
        }
        holder.itemView.check_box.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                currentTask.done = true
            } else if (!isChecked) {
                currentTask.done = false
            }
            onClickDone(currentTask)
            notifyDataSetChanged()
        }
    }

    fun setData(task: List<Task>) {
        this.taskList = task
        notifyDataSetChanged()
    }

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
