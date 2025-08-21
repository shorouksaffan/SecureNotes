package com.example.personalnotesapp.data.mapper

import com.example.personalnotesapp.data.local.entity.NoteEntity
import com.example.personalnotesapp.domain.model.Note

object NoteMapper {

    fun toDomain(noteEntity: NoteEntity): Note {
        return Note(
            id = noteEntity.id,
            title = noteEntity.title,
            content = noteEntity.content,
            timestamp = noteEntity.timestamp,
            isPrivate = noteEntity.isPrivate,
            color = noteEntity.color
        )
    }

    fun toEntity(note: Note): NoteEntity {
        return NoteEntity(
            id = note.id,
            title = note.title,
            content = note.content,
            timestamp = note.timestamp,
            isPrivate = note.isPrivate,
            color = note.color
        )
    }
    fun toDomainList(entities: List<NoteEntity>): List<Note> {
        return entities.map { toDomain(it) }
    }
}