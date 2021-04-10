package com.example.todolist.presentation.presenters.taskListPresenter

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.presentation.taskList.TaskListAdapter

interface TaskListPresenter {
    suspend fun initRecyclerView(rv: RecyclerView, nc: NavController, ct: Context)
    suspend fun initSortButton(nc: NavController, sort: Button)
    fun refreshFragment(nc: NavController)
}