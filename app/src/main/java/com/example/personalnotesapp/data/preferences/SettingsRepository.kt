package com.example.personalnotesapp.data.preferences

import androidx.datastore.dataStore
import com.example.personalnotesapp.UserSettings
import com.example.personalnotesapp.utils.ProtoDataStore
import kotlinx.coroutines.flow.Flow

class SettingsRepository(
    private val protoDataStore: ProtoDataStore
) {
    val settingsFlow: Flow<UserSettings> = protoDataStore.data

    suspend fun getSettings(): UserSettings {
        return protoDataStore.getSettings()
    }

    suspend fun saveSettings(newSettings: UserSettings) {
        protoDataStore.saveSettings(newSettings)
    }
}
