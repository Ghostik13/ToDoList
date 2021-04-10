package com.example.todolist.presentation.presenters.taskAddPresenter

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.text.TextUtils
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.todolist.DateCreator
import com.example.todolist.R
import com.example.todolist.data.TaskDatabase
import com.example.todolist.data.TaskRepositoryImpl
import com.example.todolist.data.model.Subtask
import com.example.todolist.data.model.Task
import com.example.todolist.presentation.taskAdd.AddTaskView
import com.example.todolist.util.LONG_DATE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@InjectViewState
class TaskAddPresenterImpl(application: Application) : TaskAddPresenter,
    MvpPresenter<AddTaskView>() {

    private lateinit var datePickerDialog: DatePickerDialog
    private val repository: TaskRepositoryImpl

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepositoryImpl(taskDao)
    }

    private var taskId: Int = 0

    override fun showDatePicker(listener: DatePickerDialog.OnDateSetListener, context: Context) {
        datePickerDialog = DatePickerDialog(
            context,
            listener,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    override fun inputCheck(nameOfTask: String): Boolean {
        return !(TextUtils.isEmpty(nameOfTask))
    }

    override fun dateSet(
        view: DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int,
        tv1: TextView,
        tv2: TextView
    ) {
        var dayOfMonthStr = dayOfMonth.toString()
        if (dayOfMonth < 10)
            dayOfMonthStr = "0$dayOfMonthStr"
        val monthRight = month + 1
        var monthStr: String = monthRight.toString()
        if (monthRight < 10)
            monthStr = "0$monthStr"
        val dateText: String = "$year" + monthStr + dayOfMonthStr
        tv1.text = DateCreator(dateText.toLong()).parsing
        tv2.text = dateText
    }

    override suspend fun putData(
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
    ) {
        val nameOfTask = et1.text.toString()
        val description = et2.text.toString()
        var date = DateCreator(LONG_DATE).today
        if (tv1.text.isNotEmpty()) {
            date = tv1.text.toString().toLong()
        }
        if (inputCheck(nameOfTask)) {
            val task = Task(
                0,
                nameOfTask,
                description,
                date,
                false
            )
            GlobalScope.launch(Dispatchers.IO) {
                repository.addTask(task)
            }
            putSubtasks(et3, et4, et5, et6, et7)
            Toast.makeText(ct, "Task added", Toast.LENGTH_SHORT).show()
            nc.navigate(R.id.action_addTaskFragment_to_toDoListFragment)
        } else {
            Toast.makeText(ct, "Please fill out the name", Toast.LENGTH_SHORT).show()
        }
    }

    override suspend fun putSubtasks(
        et1: EditText,
        et2: EditText,
        et3: EditText,
        et4: EditText,
        et5: EditText
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val subTask1 = et1.text.toString()
            val subTask2 = et2.text.toString()
            val subTask3 = et3.text.toString()
            val subTask4 = et4.text.toString()
            val subTask5 = et5.text.toString()
            taskId = withContext(Dispatchers.IO) {
                repository.readLastId()
            }
            val subtask1 = Subtask(
                0,
                taskId,
                subTask1,
                false
            )
            val subtask2 = Subtask(
                0,
                taskId,
                subTask2,
                false
            )
            val subtask3 = Subtask(
                0,
                taskId,
                subTask3,
                false
            )
            val subtask4 = Subtask(
                0,
                taskId,
                subTask4,
                false
            )
            val subtask5 = Subtask(
                0,
                taskId,
                subTask5,
                false
            )
            GlobalScope.launch(Dispatchers.IO) {
                repository.addSubTask(subtask1)
                repository.addSubTask(subtask2)
                repository.addSubTask(subtask3)
                repository.addSubTask(subtask4)
                repository.addSubTask(subtask5)
            }
        }
    }
}