package com.example.personalnotesapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalnotesapp.data.local.preferences.SettingsRepository
import com.example.personalnotesapp.data.model.UserSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    private val _settings = MutableStateFlow(UserSettings())
    val settings = _settings

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _settings.value = settingsRepository.getSettings()
        }
    }

    fun saveSettings(settings: UserSettings) {
        viewModelScope.launch {
            settingsRepository.saveSettings(settings)
            _settings.value = settings
        }
    }
}