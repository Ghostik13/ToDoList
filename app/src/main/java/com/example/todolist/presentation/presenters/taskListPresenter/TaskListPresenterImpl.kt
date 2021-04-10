package com.example.todolist.presentation.presenters.taskListPresenter

import android.app.Application
import android.content.Context
import android.widget.Button
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.todolist.R
import com.example.todolist.data.TaskDatabase
import com.example.todolist.data.TaskRepositoryImpl
import com.example.todolist.presentation.presenters.taskListPresenter.TaskListPresenter
import com.example.todolist.presentation.taskList.TaskListAdapter
import com.example.todolist.presentation.taskList.TaskListFragmentDirections
import com.example.todolist.presentation.taskList.TaskListView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@InjectViewState
class TaskListPresenterImpl(application: Application) : TaskListPresenter,
    MvpPresenter<TaskListView>() {

    private val repository: TaskRepositoryImpl
    private var flag = 0

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
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
                    refreshFragment(nc)
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