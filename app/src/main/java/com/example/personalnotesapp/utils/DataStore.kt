package com.example.personalnotesapp.utils

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "app_prefs",
    produceMigrations = { context ->
        listOf(
            SharedPreferencesMigration(context, "app_prefs")
        )
    }
)

class DataStore(context: Context) {
    private val dataStore = context.dataStore

    suspend fun saveData(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { prefs ->
            prefs[dataStoreKey] = value
        }
    }

    fun getData(key: String): Flow<String> {
        val dataStoreKey = stringPreferencesKey(key)
        return dataStore.data.map { prefs ->
            prefs[dataStoreKey] ?: ""
        }
    }
}