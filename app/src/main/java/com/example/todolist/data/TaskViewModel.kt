package com.example.todolist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
    }

    suspend fun readAllDataByDate(): List<Task> {
        return repository.readAllDataByDate()
    }

    suspend fun readAllDataByDone(): List<Task> {
        return repository.readAllDataByDone()
    }

    suspend fun readCurrentSubTaskData(currentTaskId: Int): List<Subtask> {
        return repository.readCurrentSubTaskData(currentTaskId)
    }

    suspend fun readLastID(): Int = withContext(Dispatchers.IO){
        repository.readLastId()
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

    fun addSubTask(subtask: Subtask) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSubTask(subtask)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun updateSubTask(subtask: Subtask) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSubTask(subtask)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

}