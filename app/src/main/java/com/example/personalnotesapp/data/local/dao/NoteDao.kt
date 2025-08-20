package com.example.personalnotesapp.data.local.dao

import androidx.room.*
import com.example.personalnotesapp.data.local.entity.NoteEntity
import com.example.personalnotesapp.domain.model.Note
import kotlinx.coroutines.flow.Flow
@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity): Int

    @Delete
    suspend fun deleteNote(note: NoteEntity): Int

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNoteById(id: Int)
}
