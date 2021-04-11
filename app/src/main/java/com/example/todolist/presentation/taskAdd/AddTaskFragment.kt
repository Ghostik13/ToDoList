package com.example.todolist.presentation.taskAdd

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.DatePicker
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.todolist.R
import com.example.todolist.domain.TaskRepository
import com.example.todolist.presentation.presenters.taskAddPresenter.TaskAddPresenter
import com.example.todolist.presentation.presenters.taskAddPresenter.TaskAddPresenterImpl
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.fragment_add_task.view.*
import kotlinx.coroutines.*

class AddTaskFragment : MvpAppCompatFragment(), AddTaskView, DatePickerDialog.OnDateSetListener {

    @ProvidePresenter
    fun provideTaskAddPresenter(): TaskAddPresenter {
        return TaskAddPresenterImpl()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)
        initTransitionAnimation(view)
        initCalendar(view)
        initAddTaskButton(view)
        return view
    }

    override fun initCalendar(v: View) {
        v.calendar_button.setOnClickListener {
            provideTaskAddPresenter().showDatePicker(this, requireContext())
        }
    }

    override fun initAddTaskButton(v: View) {
        v.add_button.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                provideTaskAddPresenter().putData(
                    v.input_name_of_task,
                    v.input_description_view,
                    v.input_subtask1,
                    v.input_subtask2,
                    v.input_subtask3,
                    v.input_subtask4,
                    v.input_subtask5,
                    v.date_long_view,
                    requireContext(),
                    findNavController()
                )
            }
        }
    }

    override fun initTransitionAnimation(v: View) {
        val animationBackground =
            AnimationUtils.loadAnimation(this.context,
                R.anim.background_animation
            )
        val animationEditField =
            AnimationUtils.loadAnimation(this.context,
                R.anim.edirfield_animation
            )
        v.animation_back.startAnimation(animationBackground)
        v.field_of_edits.startAnimation(animationEditField)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        provideTaskAddPresenter().dateSet(view, year, month, dayOfMonth, date_view, date_long_view)
    }
}
