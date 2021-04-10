package com.example.todolist.presentation.presenters.taskAddPresenter

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.NavController

interface TaskAddPresenter {
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
    suspend fun putData(
        et1: EditText,
        et2: EditText,
        et3: EditText,
        et4: EditText,
        et5: EditText,
        et6: EditText,
        et7: EditText,
        tv1: TextView,
        ct: Context,
        nc: NavController
    )
    suspend fun putSubtasks(
        et1: EditText,
        et2: EditText,
        et3: EditText,
        et4: EditText,
        et5: EditText
    )
}