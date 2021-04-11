package com.example.todolist.presentation.presenters.taskListPresenter

import android.content.Context
import android.widget.Button
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.todolist.R
import com.example.todolist.ToDoApplication
import com.example.todolist.data.TaskRepositoryImpl
import com.example.todolist.presentation.taskList.TaskListAdapter
import com.example.todolist.presentation.taskList.TaskListFragmentDirections
import com.example.todolist.presentation.taskList.TaskListView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@InjectViewState
class TaskListPresenterImpl @Inject constructor() :
    TaskListPresenter,
    MvpPresenter<TaskListView>() {

    private var flag = 0

    private val repository: TaskRepositoryImpl
    init {
        val taskDao = ToDoApplication.dao
        repository = TaskRepositoryImpl(taskDao)
    }

    override suspend fun initRecyclerView(
        rv: RecyclerView,
        nc: NavController,
        ct: Context
    ) {
        val adapter =
            TaskListAdapter({ task ->
                GlobalScope.launch(Dispatchers.IO) { repository.deleteTask(task) }
                refreshFragment(nc)
            }, { task ->
                val action =
                    TaskListFragmentDirections.actionTaskListFragmentToDetailTaskFragment(
                        task
                    )
                nc.navigate(action)
            }, { task ->
                GlobalScope.launch(Dispatchers.IO) {
                    repository.updateTask(task)
                    withContext(Dispatchers.Main){
                        refreshFragment(nc)
                    }
                }
            })
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(ct)
        GlobalScope.launch(Dispatchers.Main) {
            val listOfData = withContext(Dispatchers.IO) {
                repository.readAllDataByDone()
            }
            adapter.setData(listOfData)
        }
    }

    override suspend fun initSortButton(nc: NavController, sort: Button) {
        val adapter =
            TaskListAdapter({ task ->
                GlobalScope.launch(Dispatchers.IO) { repository.deleteTask(task) }
                refreshFragment(nc)
            }, { task ->
                val action =
                    TaskListFragmentDirections.actionTaskListFragmentToDetailTaskFragment(
                        task
                    )
                nc.navigate(action)
            }, { task ->
                GlobalScope.launch(Dispatchers.IO) {
                    repository.updateTask(task)
                    refreshFragment(nc)
                }
            })
        if (flag == 0) {
            GlobalScope.launch(Dispatchers.Main) {
                val listOfData = withContext(Dispatchers.IO) {
                    repository.readAllDataByDate()
                }
                adapter.setData(listOfData)
            }
            flag = 1
            sort.setBackgroundResource(R.drawable.sort_button_pressed)
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                val listOfData = withContext(Dispatchers.IO) {
                    repository.readAllDataByDone()
                }
                adapter.setData(listOfData)
            }
            flag = 0
            sort.setBackgroundResource(R.drawable.sort_button_normal)
        }
    }

    override fun refreshFragment(nc: NavController) {
        nc.navigate(R.id.action_toDoListFragment_to_addTaskFragment)
        nc.navigate(R.id.action_addTaskFragment_to_toDoListFragment)
    }


}