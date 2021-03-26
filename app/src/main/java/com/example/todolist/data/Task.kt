package com.example.todolist.data

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
    val date: Long
) : Parcelable

@Parcelize
@Entity(tableName = "subtask_table")
data class Subtask(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val task_id: Int,
    val name: String,
    val completed: Boolean
) : Parcelable