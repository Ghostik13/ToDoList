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
import androidx.navigation.fragment.navArgs
import com.example.todolist.data.Task
import com.example.todolist.data.TaskViewModel
import kotlinx.android.synthetic.main.fragment_detail_task.*
import kotlinx.android.synthetic.main.fragment_detail_task.view.*
import kotlinx.android.synthetic.main.fragment_detail_task.view.animation_back
import java.util.*

class DetailTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private val args by navArgs<DetailTaskFragmentArgs>()

    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_task, container, false)
        initArgs(view)
        initTransitionAnimation(view)
        view.update_add_button.setOnClickListener {
            updateTask()
        }
        view.update_calendar_button.setOnClickListener {
            showDatePicker()
        }
        return view
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
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val nameOfTask = update_input_name_of_task.text.toString()
        val description = update_description_view.text.toString()
        var date = DateCreator(LONG_DATE).today
        if (update_long_date_view.text.isNotEmpty()) {
            date = update_long_date_view.text.toString().toLong()
        }
        if (inputCheck(nameOfTask)) {
            val updatedTask = Task(args.currentTask.id, nameOfTask, description, date)
            mTaskViewModel.updateTask(updatedTask)
            Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
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
}


