package com.example.personalnotesapp.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalnotesapp.data.model.Note
import com.example.personalnotesapp.data.repository.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes: StateFlow<List<Note>> = repository.getAllNotes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}