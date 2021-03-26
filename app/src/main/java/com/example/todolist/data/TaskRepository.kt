package com.example.todolist.data

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {

    val readAllData: LiveData<List<Task>> = taskDao.readAllData()
    val readAllDataByDate: LiveData<List<Task>> = taskDao.readAllDataByDate()
    val readAllSubtaskData: LiveData<List<Subtask>> = taskDao.readAllSubTaskData()
    val readLastID: LiveData<Int> = taskDao.readLastID()

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
    suspend fun addSubTask(subtask: Subtask) {
        taskDao.addSubTask(subtask)
    }

    suspend fun updateSubTask(subtask: Subtask) {
        taskDao.updateSubTask(subtask)
    }

    suspend fun deleteSubTask(subtask: Subtask) {
        taskDao.deleteSubTask(subtask)
    }
}