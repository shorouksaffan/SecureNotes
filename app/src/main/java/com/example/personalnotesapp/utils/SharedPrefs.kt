package com.example.personalnotesapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPrefs(context: Context) {
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        sharedPrefs.edit { putString(key, value) }
    }

    fun getData(key: String): String? {
        return sharedPrefs.getString(key, null)
    }
}