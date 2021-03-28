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
import com.example.todolist.data.Subtask
import com.example.todolist.data.Task
import com.example.todolist.data.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.fragment_add_task.view.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var mTaskViewModel: TaskViewModel
    private var taskId: Int = 0
    private var flag = 0

    private val scope = CoroutineScope(Dispatchers.Main + CoroutineName("CScope"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        initTransitionAnimation(view)
        initCalendar(view)
        initAddTaskButton(view)
        initAddSubtaskButton(view)
        return view
    }

    private fun initCalendar(view: View) {
        view.calendar_button.setOnClickListener {
            showDatePicker()
        }
    }

    private fun initAddTaskButton(view: View) {
        view.add_button.setOnClickListener {
            putData()
        }
    }

    private fun initTransitionAnimation(view: View) {
        val animationBackground =
            AnimationUtils.loadAnimation(this.context, R.anim.background_animation)
        val animationEditField =
            AnimationUtils.loadAnimation(this.context, R.anim.edirfield_animation)
        view.animation_back.startAnimation(animationBackground)
        view.field_of_edits.startAnimation(animationEditField)
    }

    private fun putData() {
        val nameOfTask = input_name_of_task.text.toString()
        val description = input_description_view.text.toString()
        var date = DateCreator(LONG_DATE).today
        if (date_long_view.text.isNotEmpty()) {
            date = date_long_view.text.toString().toLong()
        }
        if (inputCheck(nameOfTask)) {
            val task = Task(0, nameOfTask, description, date, false)
            mTaskViewModel.addTask(task)
            putSubtasks()
            Toast.makeText(requireContext(), "Task added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addTaskFragment_to_toDoListFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out the name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun putSubtasks() {
        val subTask1 = input_subtask1.text.toString()
        val subTask2 = input_subtask2.text.toString()
        val subTask3 = input_subtask3.text.toString()
        val subTask4 = input_subtask4.text.toString()
        val subTask5 = input_subtask5.text.toString()
        scope.launch(Dispatchers.Main) {
            taskId = mTaskViewModel.readLastID()
            val subtask1 = Subtask(0, taskId, subTask1, false)
            val subtask2 = Subtask(0, taskId, subTask2, false)
            val subtask3 = Subtask(0, taskId, subTask3, false)
            val subtask4 = Subtask(0, taskId, subTask4, false)
            val subtask5 = Subtask(0, taskId, subTask5, false)
            mTaskViewModel.addSubTask(subtask1)
            mTaskViewModel.addSubTask(subtask2)
            mTaskViewModel.addSubTask(subtask3)
            mTaskViewModel.addSubTask(subtask4)
            mTaskViewModel.addSubTask(subtask5)
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
        date_view.text = DateCreator(dateText.toLong()).parsing
        date_long_view.text = dateText
    }

    private fun initAddSubtaskButton(view: View) {
        view.add_subtask_button.setOnClickListener {
            when (flag) {
                0 -> {
                    view.input_subtask1.visibility = View.VISIBLE
                    flag = 1
                }
                1 -> {
                    view.input_subtask2.visibility = View.VISIBLE
                    flag = 2
                }
                2 -> {
                    view.input_subtask3.visibility = View.VISIBLE
                    flag = 3
                }
                3 -> {
                    view.input_subtask4.visibility = View.VISIBLE
                    flag = 4
                }
                4 -> {
                    view.input_subtask5.visibility = View.VISIBLE
                    flag = 5
                }
            }
        }
    }
}
