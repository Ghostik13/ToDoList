package com.example.todolist.presentation.presenters.taskDetailPresenter

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
import com.example.todolist.presentation.taskDetail.DetailTaskFragmentArgs
import com.example.todolist.presentation.taskDetail.DetailTaskView
import com.example.todolist.util.LONG_DATE
import kotlinx.android.synthetic.main.fragment_detail_task.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@InjectViewState
class TaskDetailPresenterImpl(application: Application) : TaskDetailPresenter,
    MvpPresenter<DetailTaskView>() {

    private lateinit var datePickerDialog: DatePickerDialog
    private val repository: TaskRepositoryImpl

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepositoryImpl(taskDao)
    }

    private lateinit var subTasks: List<Subtask>

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

    override fun initArgs(
        et1: EditText,
        et2: EditText,
        tv1: TextView,
        tv2: TextView,
        args: DetailTaskFragmentArgs
    ) {
        et1.setText(args.currentTask.name)
        et2.setText(args.currentTask.description)
        tv1.update_long_date_view.text = (args.currentTask.date.toString())
        tv2.updated_date_view.text = DateCreator(args.currentTask.date).parsing
    }

    override suspend fun initSub(
        et1: EditText,
        et2: EditText,
        et3: EditText,
        et4: EditText,
        et5: EditText,
        args: DetailTaskFragmentArgs
    ) {
        subTasks = withContext(Dispatchers.IO) {
            repository.readCurrentSubTaskData(args.currentTask.id)
        }
        et1.setText(subTasks[0].name)
        et2.setText(subTasks[1].name)
        et3.setText(subTasks[2].name)
        et4.setText(subTasks[3].name)
        et5.setText(subTasks[4].name)
    }

    override suspend fun updateData(
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
    ) {
        val nameOfTask = et1.text.toString()
        val description = et2.text.toString()
        var date = DateCreator(LONG_DATE).today
        if (tv1.text.isNotEmpty()) {
            date = tv1.text.toString().toLong()
        }
        if (inputCheck(nameOfTask)) {
            val updatedTask = Task(
                args.currentTask.id,
                nameOfTask,
                description,
                date,
                false
            )
            GlobalScope.launch(Dispatchers.IO) {
                repository.updateTask(updatedTask)
            }
            val subTask1 = et3.text.toString()
            val subTask2 = et4.text.toString()
            val subTask3 = et5.text.toString()
            val subTask4 = et6.text.toString()
            val subTask5 = et7.text.toString()
            val updatedSubtask1 = Subtask(
                subTasks[0].id,
                args.currentTask.id,
                subTask1,
                false
            )
            val updatedSubtask2 = Subtask(
                subTasks[1].id,
                args.currentTask.id,
                subTask2,
                false
            )
            val updatedSubtask3 = Subtask(
                subTasks[2].id,
                args.currentTask.id,
                subTask3,
                false
            )
            val updatedSubtask4 = Subtask(
                subTasks[3].id,
                args.currentTask.id,
                subTask4,
                false
            )
            val updatedSubtask5 = Subtask(
                subTasks[4].id,
                args.currentTask.id,
                subTask5,
                false
            )
            GlobalScope.launch(Dispatchers.IO) {
                repository.updateSubTask(updatedSubtask1)
                repository.updateSubTask(updatedSubtask2)
                repository.updateSubTask(updatedSubtask3)
                repository.updateSubTask(updatedSubtask4)
                repository.updateSubTask(updatedSubtask5)
            }
            Toast.makeText(ct, "Task updated", Toast.LENGTH_SHORT).show()
            nc.navigate(R.id.action_detailTaskFragment_to_task_list_fragment)
        } else {
            Toast.makeText(ct, "Please fill out the name", Toast.LENGTH_SHORT).show()
        }
    }
}