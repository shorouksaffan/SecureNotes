package com.example.personalnotesapp.data.repository

import com.example.personalnotesapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note): Int
    suspend fun deleteNote(note: Note): Int
    suspend fun deleteNoteById(id: Int)
}
