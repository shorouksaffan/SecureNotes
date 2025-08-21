package com.example.personalnotesapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.example.personalnotesapp.data.preferences.SettingsRepository
import com.example.personalnotesapp.data.repository.NoteRepository
import com.example.personalnotesapp.domain.model.UserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val noteRepository: NoteRepository
) : ViewModel() {

    val settings = settingsRepository.settingsFlow.asLiveData()

    private fun saveSettings(newSettings: UserSettings) {
        viewModelScope.launch {
            settingsRepository.saveSettings(newSettings)
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