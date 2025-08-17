package com.example.personalnotesapp.data.local.encryption

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.core.content.edit

class SecurePreferences private constructor(context: Context) {

    private val masterKey: MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedSharedPrefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_notes_prefs", // File name
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun savePassword(password: String) {
        encryptedSharedPrefs.edit {
            putString("user_lock_password", password)
        }
    }

    fun getPassword(): String? {
        return encryptedSharedPrefs.getString("user_lock_password", null)
    }

    fun clearPassword() {
        encryptedSharedPrefs.edit {
            remove("user_lock_password")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SecurePreferences? = null

        fun getInstance(context: Context): SecurePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SecurePreferences(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}