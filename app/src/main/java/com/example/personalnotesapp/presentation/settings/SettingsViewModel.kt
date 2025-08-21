package com.example.personalnotesapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.example.personalnotesapp.data.preferences.SettingsRepository
import com.example.personalnotesapp.domain.model.UserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    // ✅ Expose as LiveData so Fragment can use `observe`
    val settings = repository.settingsFlow.asLiveData()

    private fun saveSettings(newSettings: UserSettings) {
        viewModelScope.launch {
            repository.saveSettings(newSettings)
        }
    }

    fun updateSettings(
        isDarkMode: Boolean,
        fontSize: Int,
        autosave: Boolean
    ) {
        val newSettings = UserSettings(
            isDarkMode = isDarkMode,
            fontSize = fontSize,
            autoSave = autosave
        )
        saveSettings(newSettings)
    }
}
