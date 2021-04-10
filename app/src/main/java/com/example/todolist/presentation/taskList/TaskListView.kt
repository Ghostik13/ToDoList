package com.example.todolist.presentation.taskList

import android.view.View
import com.arellomobile.mvp.MvpView

interface TaskListView: MvpView {
    fun initRecyclerView()
    fun initSortButton(v: View)
    fun initFab(v: View)
    fun initSwitchTheme(v: View)
}