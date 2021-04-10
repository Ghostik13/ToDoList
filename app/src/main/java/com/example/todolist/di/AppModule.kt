package com.example.todolist.di

import android.app.Application
import com.example.todolist.data.TaskDatabase
import com.example.todolist.domain.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDataBase(application: Application): TaskDao {
        return TaskDatabase.getDatabase(application).taskDao()
    }
}

