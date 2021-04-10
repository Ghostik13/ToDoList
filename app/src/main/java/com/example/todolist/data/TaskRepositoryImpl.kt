package com.example.todolist.data

import com.example.todolist.data.model.Subtask
import com.example.todolist.data.model.Task
import com.example.todolist.domain.TaskDao
import com.example.todolist.domain.TaskRepository

class TaskRepositoryImpl(private val taskDao: TaskDao): TaskRepository {

    override suspend fun readLastId(): Int
            = taskDao.readLastID()

    override suspend fun readAllDataByDate(): List<Task>
            = taskDao.readAllDataByDate()

    override suspend fun readAllDataByDone(): List<Task>
            = taskDao.readAllDataByDone()

    override suspend fun readCurrentSubTaskData(currentTaskId: Int): List<Subtask>
            = taskDao.readCurrentSubTaskData(currentTaskId)

    override suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override suspend fun addSubTask(subtask: Subtask) {
        taskDao.addSubTask(subtask)
    }

    override suspend fun updateSubTask(subtask: Subtask) {
        taskDao.updateSubTask(subtask)
    }
}