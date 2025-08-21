package com.example.personalnotesapp.utils

import android.content.Context
import android.net.Uri
import java.util.Date

object FileExporter {

    fun exportNoteToTxt(context: Context, uri: Uri, title: String, body: String, timestamp: Long): Boolean {
        return try {
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                val content = buildString {
                    append("Title: $title\n\n")
                    append(body)
                    append("\n\nCreated at: ${Date(timestamp)}")
                }
                outputStream.write(content.toByteArray())
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}