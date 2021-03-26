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
import androidx.navigation.fragment.findNavController
import com.example.todolist.data.Subtask
import com.example.todolist.data.Task
import com.example.todolist.data.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.fragment_add_task.view.*
import java.util.*

class AddTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        initTransitionAnimation(view)
        initCalendar(view)
        initAddTaskButton(view)
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
        val subTask1 = input_subtask1.text.toString()
        val subTask2 = input_subtask2.text.toString()
        val subTask3 = input_subtask3.text.toString()
        if (inputCheck(nameOfTask)) {
            val task = Task(0, nameOfTask, description, date)
            mTaskViewModel.addTask(task)
            val subtask1 = Subtask(0, task.id, subTask1, false)
            val subtask2 = Subtask(0, task.id, subTask2, false)
            val subtask3 = Subtask(0, task.id, subTask3, false)
            mTaskViewModel.addSubTask(subtask1)
            mTaskViewModel.addSubTask(subtask2)
            mTaskViewModel.addSubTask(subtask3)
            Toast.makeText(requireContext(), "Task added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addTaskFragment_to_toDoListFragment)
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
        date_view.text = DateCreator(dateText.toLong()).parsing
        date_long_view.text = dateText
    }
}