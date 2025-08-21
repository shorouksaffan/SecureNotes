package com.example.personalnotesapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val isDarkMode: Boolean = false,
    val fontSize: Int = 16,
    val autoSave: Boolean = true,
)