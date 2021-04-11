package com.example.todolist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.data.model.Subtask
import com.example.todolist.data.model.Task
import com.example.todolist.domain.TaskDao

@Database(entities = [Task::class, Subtask::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}
