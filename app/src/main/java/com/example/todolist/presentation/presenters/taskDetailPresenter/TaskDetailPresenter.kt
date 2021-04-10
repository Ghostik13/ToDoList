package com.example.todolist.presentation.presenters.taskDetailPresenter

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.NavController
import com.example.todolist.presentation.taskDetail.DetailTaskFragmentArgs

interface TaskDetailPresenter {
    fun showDatePicker(listener: DatePickerDialog.OnDateSetListener, context: Context)
    fun inputCheck(nameOfTask: String): Boolean
    fun dateSet(
        view: DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int,
        tv1: TextView,
        tv2: TextView
    )
    fun initArgs(
        et1: EditText,
        et2: EditText,
        tv1: TextView,
        tv2: TextView,
        args: DetailTaskFragmentArgs
    )
    suspend fun initSub(
        et1: EditText,
        et2: EditText,
        et3: EditText,
        et4: EditText,
        et5: EditText,
        args: DetailTaskFragmentArgs
    )
    suspend fun updateData(
        et1: EditText,
        et2: EditText,
        et3: EditText,
        et4: EditText,
        et5: EditText,
        et6: EditText,
        et7: EditText,
        tv1: TextView,
        ct: Context,
        nc: NavController,
        args: DetailTaskFragmentArgs
    )
}