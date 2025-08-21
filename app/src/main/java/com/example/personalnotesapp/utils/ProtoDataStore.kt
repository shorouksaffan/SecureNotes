package com.example.personalnotesapp.utils

import android.content.Context
import androidx.datastore.dataStore
import com.example.personalnotesapp.domain.model.UserSettings
import com.example.personalnotesapp.domain.model.UserSettingsSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

private val Context.userSettingsDataStore by dataStore(
    fileName = "user_settings.json",
    serializer = UserSettingsSerializer
)

class ProtoDataStore(context: Context) {
    private val dataStore = context.userSettingsDataStore

    val data: Flow<UserSettings> = dataStore.data

    suspend fun saveSettings(settings: UserSettings) {
        dataStore.updateData { settings }
    }

    suspend fun getSettings(): UserSettings {
        return dataStore.data.first()
    }
}
