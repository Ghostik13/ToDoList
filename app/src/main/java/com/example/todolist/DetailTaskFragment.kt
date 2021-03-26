package com.example.todolist

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
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
import kotlinx.android.synthetic.main.fragment_add_task.*
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
        view.updated_date_view.text = parsingDate((args.currentTask.date))
    }

    private fun updateTask() {
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val nameOfTask = update_input_name_of_task.text.toString()
        val description = update_description_view.text.toString()
        var date = dateCreate()
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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var dayOfMonthStr = dayOfMonth.toString()
        if (dayOfMonth < 10)
            dayOfMonthStr = "0$dayOfMonthStr"
        val monthRight = month + 1
        var monthStr: String = monthRight.toString()
        if (monthRight < 10)
            monthStr = "0$monthStr"
        val dateText: String = "$year" + monthStr + dayOfMonthStr
        updated_date_view.text = parsingDate(dateText.toLong())
        update_long_date_view.text = dateText
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateCreate(): Long {
        val sdf = SimpleDateFormat("YYYY/MM/dd")
        val date: String = sdf.format(Date(System.currentTimeMillis()))
        val today = date.replace(Regex("[/]+"), "")
        return today.toLong()
    }
}


