package com.example.todolist.domain

import com.example.todolist.data.model.Subtask
import com.example.todolist.data.model.Task

interface TaskRepository {
    suspend fun readLastId(): Int
    suspend fun readAllDataByDate(): List<Task>
    suspend fun readAllDataByDone(): List<Task>
    suspend fun readCurrentSubTaskData(currentTaskId: Int): List<Subtask>
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun addSubTask(subtask: Subtask)
    suspend fun updateSubTask(subtask: Subtask)
}