package com.example.personalnotesapp.data.repository

import com.example.personalnotesapp.data.local.dao.NoteDao
import com.example.personalnotesapp.data.local.entity.NoteEntity
import com.example.personalnotesapp.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotes().map { entities ->
            entities.mapNotNull { it.toDomainOrNull() }
        }


    override suspend fun getNoteById(id: Int): Note? =
        noteDao.getNoteById(id)?.toDomain()

    override suspend fun insertNote(note: Note): Long =
        noteDao.insertNote(note.toEntity())

    override suspend fun updateNote(note: Note): Int =
        noteDao.updateNote(note.toEntity())

    override suspend fun deleteNote(note: Note): Int =
        noteDao.deleteNote(note.toEntity())

    override suspend fun deleteNoteById(id: Int) {
        noteDao.deleteNoteById(id)
    }

    /* ---------- Mappers ---------- */
    private fun NoteEntity.toDomain(): Note = Note(
        id = id,
        title = title ?: "",
        content = content ?: "",
        timestamp = timestamp,
        isPrivate = isPrivate ?: false,
        color = color
    )

    private fun NoteEntity.toDomainOrNull(): Note? = try {
        toDomain()
    } catch (e: Exception) {
        null // skip corrupt entries
    }

    private fun Note.toEntity(): NoteEntity = NoteEntity(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        isPrivate = isPrivate,
        color = color
    )
}
