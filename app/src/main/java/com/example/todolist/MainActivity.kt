package com.example.todolist

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.todolist.util.NIGHT_MODE
import com.example.todolist.util.SAVED_STATE

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (loadState()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.MyThemeNight)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            setTheme(R.style.MyTheme)
        }
        setContentView(R.layout.activity_main)
    }

    private fun loadState(): Boolean {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(SAVED_STATE, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(NIGHT_MODE, false)
    }
}