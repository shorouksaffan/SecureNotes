package com.example.personalnotesapp.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalnotesapp.data.model.Note
import com.example.personalnotesapp.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class NotesState {
    data class Success(val notes: List<Note>) : NotesState()
    data class Error(val message: String) : NotesState()
    object Loading : NotesState()
}

class NotesViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notesState = MutableStateFlow<NotesState>(NotesState.Loading)
    val notesState: StateFlow<NotesState> = _notesState

    fun getAllNotes() {
        viewModelScope.launch {
            _notesState.value = NotesState.Loading
            try {
                repository.getAllNotes().collect { notes ->
                    _notesState.value = NotesState.Success(notes)
                }
            } catch (e: Exception) {
                _notesState.value = NotesState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.deleteNote(note)
                getAllNotes() // refresh list after deletion
            } catch (e: Exception) {
                _notesState.value = NotesState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addOrUpdateNote(note: Note) {
        viewModelScope.launch {
            try {
                if (note.id == 0) {
                    repository.insertNote(note)
                } else {
                    repository.updateNote(note)
                }
                getAllNotes()
            } catch (e: Exception) {
                _notesState.value = NotesState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
