package com.example.personalnotesapp.utils

import android.content.Context
import androidx.datastore.dataStore
import com.example.personalnotesapp.data.local.preferences.UserSettings
import com.example.personalnotesapp.data.model.UserSettingsSerializer
import kotlinx.coroutines.flow.first

private val Context.dataStore by dataStore("user_settings.pb", UserSettingsSerializer)

class ProtoDataStore(context: Context) {
    private val dataStore = context.dataStore

    suspend fun saveSettings(settings: UserSettings) {
        dataStore.updateData { settings }
    }

    suspend fun getSettings(): UserSettings {
        return dataStore.data.first()
    }

}