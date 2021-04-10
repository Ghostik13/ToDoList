package com.example.todolist.di

import android.app.Application
import com.example.todolist.ToDoApplication
import com.example.todolist.data.TaskDatabase
import com.example.todolist.domain.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
@Module
@InstallIn(ToDoApplication::class)
object AppModule {

    @Provides
    fun provideDataBase(application: Application): TaskDao {
        return TaskDatabase.getDatabase(application).taskDao()
    }
}

