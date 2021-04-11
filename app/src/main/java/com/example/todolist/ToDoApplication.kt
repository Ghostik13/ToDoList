package com.example.todolist

import android.app.Application
import com.example.todolist.di.DatabaseModule
import com.example.todolist.domain.TaskDao
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToDoApplication : Application() {

    companion object {
        lateinit var dao: TaskDao
    }

    override fun onCreate() {
        super.onCreate()
        dao = DatabaseModule
            .provideTaskDao(
                DatabaseModule
                    .provideAppDatabase(applicationContext)
            )
    }
}