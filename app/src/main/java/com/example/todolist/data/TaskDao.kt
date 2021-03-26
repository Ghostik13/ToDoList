package com.example.todolist.data

import androidx.lifecycle.LiveData
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
    suspend fun deleteSubTask(subtask: Subtask)

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Task>>

    @Query("SELECT MAX(id) FROM task_table")
    fun readLastID(): LiveData<Int>

    @Query("SELECT * FROM task_table ORDER BY date ASC")
    fun readAllDataByDate(): LiveData<List<Task>>

    @Query("SELECT * FROM subtask_table WHERE task_id IN (SELECT id FROM task_table)")
    fun readAllSubTaskData(): LiveData<List<Subtask>>
}