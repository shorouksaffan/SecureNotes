package com.example.personalnotesapp.presentation.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalnotesapp.domain.model.Note
import com.example.personalnotesapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _note = MutableLiveData<Note?>()
    val note: LiveData<Note?> get() = _note

    fun loadNote(id: Int) {
        viewModelScope.launch {
            _note.value = repository.getNoteById(id)
        }
    }

    fun saveNote(id: Int, title: String, content: String) {
        viewModelScope.launch {
            if (id == 0) {
                repository.insertNote(Note(title = title, content = content))
            } else {
                repository.updateNote(Note(id = id, title = title, content = content))
            }
        }
    }
    fun deleteNote(id: Int) {
        viewModelScope.launch {
            repository.deleteNoteById(id)
        }
    }
}
