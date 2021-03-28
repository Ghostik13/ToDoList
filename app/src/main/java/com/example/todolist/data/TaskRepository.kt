package com.example.todolist.data

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun readLastId(): Int
            = taskDao.readLastID()

    suspend fun readAllDataByDate(): List<Task>
            = taskDao.readAllDataByDate()

    suspend fun readAllDataByDone(): List<Task>
            = taskDao.readAllDataByDone()

    suspend fun readCurrentSubTaskData(currentTaskId: Int): List<Subtask>
            = taskDao.readCurrentSubTaskData(currentTaskId)

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
}