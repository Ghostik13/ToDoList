package com.example.todolist.presentation.taskDetail

import android.view.View
import com.arellomobile.mvp.MvpView

interface DetailTaskView: MvpView {
    fun initUpdateButton(v: View)
    fun initCalendar(v: View)
    fun initSubtasks(v: View)
    fun initTransitionAnimation(v: View)
    fun initArgs(v: View)
}