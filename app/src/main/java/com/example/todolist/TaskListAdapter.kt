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
        holder.itemView.date_in_holder.text = parsingDate(currentTask.date)
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

    private fun convertDate(month: String?): String? {
        var monthString: String? = null
        when (month) {
            "01" -> monthString = "Jan"
            "02" -> monthString = "Feb"
            "03" -> monthString = "Mar"
            "04" -> monthString = "Apr"
            "05" -> monthString = "May"
            "06" -> monthString = "Jun"
            "07" -> monthString = "Jul"
            "08" -> monthString = "Aug"
            "09" -> monthString = "Sep"
            "10" -> monthString = "Oct"
            "11" -> monthString = "Nov"
            "12" -> monthString = "Dec"
        }
        return monthString
    }

    private fun parsingDate(date: Long): String? {
        val parsedDate = date.toString().substring(4, date.toString().length)
        var day = parsedDate.substring(2, parsedDate.length)
        if (day.startsWith("0"))
            day = day.replaceFirst("0", "")
        var month: String? = parsedDate.substring(0, 2)
        month = convertDate(month)
        return "$day $month"
    }

    fun setData(task: List<Task>) {
        this.taskList = task
        notifyDataSetChanged()
    }

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
