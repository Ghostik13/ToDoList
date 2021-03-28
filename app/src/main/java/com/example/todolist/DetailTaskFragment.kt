package com.example.todolist

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.data.Subtask
import com.example.todolist.data.Task
import com.example.todolist.data.TaskViewModel
import kotlinx.android.synthetic.main.fragment_detail_task.*
import kotlinx.android.synthetic.main.fragment_detail_task.view.*
import kotlinx.android.synthetic.main.fragment_detail_task.view.add_subtask_button
import kotlinx.android.synthetic.main.fragment_detail_task.view.animation_back
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class DetailTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private val args by navArgs<DetailTaskFragmentArgs>()

    private lateinit var subTasks: List<Subtask>
    private lateinit var mTaskViewModel: TaskViewModel
    private var flag = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_task, container, false)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        initArgs(view)
        initSubtasks(view)
        initTransitionAnimation(view)
        view.update_add_button.setOnClickListener {
            updateTask()
        }
        view.update_calendar_button.setOnClickListener {
            showDatePicker()
        }
        initAddSubtaskButton(view)
        return view
    }

    private fun initSubtasks(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            subTasks = mTaskViewModel.readCurrentSubTaskData(args.currentTask.id)
            view.update_subtask1.setText(subTasks[0].name)
            view.update_subtask2.setText(subTasks[1].name)
            view.update_subtask3.setText(subTasks[2].name)
            view.update_subtask4.setText(subTasks[3].name)
            view.update_subtask5.setText(subTasks[4].name)
            if (subTasks[0].name != "") {
                update_subtask1.visibility = View.VISIBLE
                flag = 1
            }
            if (subTasks[1].name != "") {
                update_subtask2.visibility = View.VISIBLE
                flag = 2
            }
            if (subTasks[2].name != "") {
                update_subtask3.visibility = View.VISIBLE
                flag = 3
            }
            if (subTasks[3].name != "") {
                update_subtask4.visibility = View.VISIBLE
                flag = 4
            }
            if (subTasks[4].name != "") {
                update_subtask5.visibility = View.VISIBLE
                flag = 5
            }
        }
    }

    private fun initTransitionAnimation(view: View) {
        val animationBackground =
            AnimationUtils.loadAnimation(this.context, R.anim.background_animation)
        view.animation_back.startAnimation(animationBackground)
    }

    private fun initArgs(view: View) {
        view.update_input_name_of_task.setText(args.currentTask.name)
        view.update_description_view.setText(args.currentTask.description)
        view.update_long_date_view.text = (args.currentTask.date.toString())
        view.updated_date_view.text = DateCreator(args.currentTask.date).parsing
    }

    private fun updateTask() {
        val nameOfTask = update_input_name_of_task.text.toString()
        val description = update_description_view.text.toString()
        var date = DateCreator(LONG_DATE).today
        if (update_long_date_view.text.isNotEmpty()) {
            date = update_long_date_view.text.toString().toLong()
        }
        if (inputCheck(nameOfTask)) {
            val updatedTask = Task(args.currentTask.id, nameOfTask, description, date, false)
            mTaskViewModel.updateTask(updatedTask)
            val subTask1 = update_subtask1.text.toString()
            val subTask2 = update_subtask2.text.toString()
            val subTask3 = update_subtask3.text.toString()
            val subTask4 = update_subtask4.text.toString()
            val subTask5 = update_subtask5.text.toString()
            mTaskViewModel.viewModelScope.launch(Dispatchers.Main) {
                val updatedSubtask1 = Subtask(subTasks[0].id, args.currentTask.id, subTask1, false)
                val updatedSubtask2 = Subtask(subTasks[1].id, args.currentTask.id, subTask2, false)
                val updatedSubtask3 = Subtask(subTasks[2].id, args.currentTask.id, subTask3, false)
                val updatedSubtask4 = Subtask(subTasks[3].id, args.currentTask.id, subTask4, false)
                val updatedSubtask5 = Subtask(subTasks[4].id, args.currentTask.id, subTask5, false)
                mTaskViewModel.updateSubTask(updatedSubtask1)
                mTaskViewModel.updateSubTask(updatedSubtask2)
                mTaskViewModel.updateSubTask(updatedSubtask3)
                mTaskViewModel.updateSubTask(updatedSubtask4)
                mTaskViewModel.updateSubTask(updatedSubtask5)
            }
            Toast.makeText(requireContext(), "Task updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detailTaskFragment_to_task_list_fragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out the name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(nameOfTask: String): Boolean {
        return !(TextUtils.isEmpty(nameOfTask))
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var dayOfMonthStr = dayOfMonth.toString()
        if (dayOfMonth < 10)
            dayOfMonthStr = "0$dayOfMonthStr"
        val monthRight = month + 1
        var monthStr: String = monthRight.toString()
        if (monthRight < 10)
            monthStr = "0$monthStr"
        val dateText: String = "$year" + monthStr + dayOfMonthStr
        updated_date_view.text = DateCreator(dateText.toLong()).parsing
        update_long_date_view.text = dateText
    }

    private fun initAddSubtaskButton(view: View) {
        view.add_subtask_button.setOnClickListener {
            when (flag) {
                0 -> {
                    view.update_subtask1.visibility = View.VISIBLE
                    flag = 1
                }
                1 -> {
                    view.update_subtask2.visibility = View.VISIBLE
                    flag = 2
                }
                2 -> {
                    view.update_subtask3.visibility = View.VISIBLE
                    flag = 3
                }
                3 -> {
                    view.update_subtask4.visibility = View.VISIBLE
                    flag = 4
                }
                4 -> {
                    view.update_subtask5.visibility = View.VISIBLE
                    flag = 5
                }
            }
        }
    }
}


