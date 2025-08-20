package com.example.personalnotesapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isPrivate: Boolean = false,
    val color: Int? = null
) {
    fun getFormattedDate(): String {
        return Date(timestamp).toString()
    }
}