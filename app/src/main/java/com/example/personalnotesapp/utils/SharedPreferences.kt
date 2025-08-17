package com.example.personalnotesapp.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun getData(key: String): String {
        return sharedPreferences.getString(key, null) ?: ""
    }
}