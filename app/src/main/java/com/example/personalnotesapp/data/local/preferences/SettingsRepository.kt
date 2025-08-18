package com.example.personalnotesapp.data.local.preferences

import com.example.personalnotesapp.data.model.UserSettings
import com.example.personalnotesapp.utils.ProtoDataStore

class SettingsRepository(private val dataStore: ProtoDataStore) {
    suspend fun saveSettings(settings: UserSettings) {
        dataStore.saveSettings(settings)
    }

    suspend fun getSettings(): UserSettings {
        return dataStore.getSettings()
    }
}
