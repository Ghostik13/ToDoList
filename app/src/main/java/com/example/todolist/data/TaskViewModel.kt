package com.example.todolist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Task>>
    val readAllDataByDate: LiveData<List<Task>>
    val readAllSubtaskData: LiveData<List<Subtask>>
    val readLastID: LiveData<Int>

    private val repository: TaskRepository

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        readAllData = repository.readAllData
        readAllDataByDate = repository.readAllDataByDate
        readAllSubtaskData = repository.readAllSubtaskData
        readLastID = repository.readLastID
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

    fun deleteSubTask(subtask: Subtask) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSubTask(subtask)
        }
    }

}