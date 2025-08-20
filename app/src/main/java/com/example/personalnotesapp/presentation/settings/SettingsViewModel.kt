package com.example.personalnotesapp.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.personalnotesapp.UserSettings
import com.example.personalnotesapp.data.preferences.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    val settings: LiveData<UserSettings> = repository.settingsFlow.asLiveData()

    fun updateSettings(isDarkMode: Boolean, fontSize: Int, autosave: Boolean) {
        viewModelScope.launch {
            val newSettings = UserSettings.newBuilder()
                .setIsDarkMode(isDarkMode)
                .setFontSize(fontSize)
                .setAutosave(autosave)
                .build()

            repository.saveSettings(newSettings)
        }
    }
}
