package com.example.todolist.presentation.taskDetail

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.DatePicker
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.todolist.R
import com.example.todolist.presentation.presenters.taskDetailPresenter.TaskDetailPresenter
import com.example.todolist.presentation.presenters.taskDetailPresenter.TaskDetailPresenterImpl
import kotlinx.android.synthetic.main.fragment_detail_task.*
import kotlinx.android.synthetic.main.fragment_detail_task.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailTaskFragment : MvpAppCompatFragment(), DetailTaskView,
    DatePickerDialog.OnDateSetListener {

    private val args by navArgs<DetailTaskFragmentArgs>()

    @ProvidePresenter
    fun provideTaskDetailPresenter(): TaskDetailPresenter {
        return TaskDetailPresenterImpl(
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_task, container, false)
        initArgs(view)
        initSubtasks(view)
        initTransitionAnimation(view)
        initUpdateButton(view)
        initCalendar(view)
        return view
    }

    override fun initUpdateButton(v: View) {
        v.update_add_button.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                provideTaskDetailPresenter().updateData(
                    update_input_name_of_task,
                    update_description_view,
                    update_subtask1,
                    update_subtask2,
                    update_subtask3,
                    update_subtask4,
                    update_subtask5,
                    update_long_date_view,
                    requireContext(),
                    findNavController(),
                    args
                )
            }
        }
    }

    override fun initCalendar(v: View) {
        v.update_calendar_button.setOnClickListener {
            provideTaskDetailPresenter().showDatePicker(this, requireContext())
        }
    }

    override fun initSubtasks(v: View) {
        GlobalScope.launch(Dispatchers.Main) {
            provideTaskDetailPresenter().initSub(
                v.update_subtask1,
                v.update_subtask2,
                v.update_subtask3,
                v.update_subtask4,
                v.update_subtask5,
                args
            )
        }
    }

    override fun initTransitionAnimation(v: View) {
        val animationBackground =
            AnimationUtils.loadAnimation(
                this.context,
                R.anim.background_animation
            )
        v.animation_back.startAnimation(animationBackground)
    }

    override fun initArgs(v: View) {
        provideTaskDetailPresenter().initArgs(
            v.update_input_name_of_task,
            v.update_description_view,
            v.update_long_date_view,
            v.updated_date_view,
            args
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        provideTaskDetailPresenter().dateSet(view, year, month, dayOfMonth, updated_date_view, update_long_date_view)
    }
}


