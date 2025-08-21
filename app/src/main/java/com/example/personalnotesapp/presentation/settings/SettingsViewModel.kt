package com.example.personalnotesapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.example.personalnotesapp.data.preferences.SettingsRepository
import com.example.personalnotesapp.data.repository.NoteRepository
import com.example.personalnotesapp.domain.model.UserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val noteRepository: NoteRepository
) : ViewModel() {

    val settings = settingsRepository.settingsFlow.asLiveData()

    private var currentSettings: UserSettings? = null


    init {
        viewModelScope.launch {
            settingsRepository.settingsFlow.collectLatest { s ->
                currentSettings = s
            }
        }
        viewModelScope.launch {
                currentSettings = settingsRepository.getSettings()
        }
    }

    private fun saveSettings(newSettings: UserSettings) {
        if (newSettings == currentSettings) return

        viewModelScope.launch {
            settingsRepository.saveSettings(newSettings)
            currentSettings = newSettings
        }
    }

    fun updateSettings(
        isDarkMode: Boolean? = null,
        fontSize: Int? = null,
        autosave: Boolean? = null
    ) {
        val base = currentSettings ?: UserSettings()
        val newSettings = base.copy(
            isDarkMode = isDarkMode ?: base.isDarkMode,
            fontSize = fontSize ?: base.fontSize,
            autoSave = autosave ?: base.autoSave
        )
        saveSettings(newSettings)
    }

    fun getAllNotesForExport(callback: (String) -> Unit) {
        viewModelScope.launch {
            noteRepository.getAllNotes().collect { notes ->
                val exportText = notes.joinToString(separator = "\n\n") { note ->
                    "Title: ${note.title}\nBody: ${note.content}\nDate: ${note.timestamp}"
                }
                callback(exportText)
            }
        }
    }
}