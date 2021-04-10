package com.example.todolist.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val date: Long,
    var done: Boolean
) : Parcelable

@Parcelize
@Entity(tableName = "subtask_table")
data class Subtask(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val taskId: Int,
    val name: String,
    val done: Boolean
) : Parcelable