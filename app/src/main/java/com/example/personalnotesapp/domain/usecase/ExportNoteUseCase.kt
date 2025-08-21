package com.example.personalnotesapp.domain.usecase

import android.content.Context
import android.net.Uri
import com.example.personalnotesapp.domain.model.Note
import com.example.personalnotesapp.utils.FileExporter

class ExportNoteUseCase {

    fun exportNote(context: Context, note: Note, uri: Uri): Boolean {
        return FileExporter.exportNoteToTxt(
            context = context,
            uri = uri,
            title = note.title,
            body = note.content,
            timestamp = note.timestamp
        )
    }
}