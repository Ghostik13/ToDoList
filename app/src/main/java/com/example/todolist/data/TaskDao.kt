package com.example.todolist.data

import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSubTask(subtask: Subtask)

    @Update
    suspend fun updateTask(task: Task)

    @Update
    suspend fun updateSubTask(subtask: Subtask)

    @Delete
    suspend fun deleteTask(task: Task)

    @Delete
    suspend fun deleteSubTask(subtask: List<Subtask>)

    @Query("SELECT MAX(id) FROM task_table")
    suspend fun readLastID(): Int

    @Query("SELECT * FROM task_table ORDER BY date ASC")
    suspend fun readAllDataByDate(): List<Task>

    @Query("SELECT * FROM task_table ORDER BY done ASC")
    suspend fun readAllDataByDone(): List<Task>

    @Query("SELECT * FROM subtask_table WHERE taskId = :currentTaskId")
    suspend fun readCurrentSubTaskData(currentTaskId: Int): List<Subtask>
}