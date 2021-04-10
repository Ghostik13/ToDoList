package com.example.todolist.presentation.taskAdd

import android.view.View
import com.arellomobile.mvp.MvpView

interface AddTaskView: MvpView {
    fun initCalendar(v: View)
    fun initAddTaskButton(v: View)
    fun initTransitionAnimation(v: View)
}