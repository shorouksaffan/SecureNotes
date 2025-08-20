package com.example.personalnotesapp.presentation.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.personalnotesapp.domain.model.Note
import com.example.personalnotesapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    val notes: LiveData<List<Note>> = repository.getAllNotes().asLiveData()
}
