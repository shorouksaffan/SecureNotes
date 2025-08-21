package com.example.personalnotesapp.local.encryption


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.personalnotesapp.data.local.encryption.SecurePreferences
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

class SecurePreferencesTest {

    private lateinit var securePreferences: SecurePreferences
    private val testPassword = "TestPassword123"

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        securePreferences = SecurePreferences.Companion.getInstance(context)
        securePreferences.clearPassword()
    }

    @Test
    fun savePassword_and_getPassword_returnsSavedPassword() {
        securePreferences.savePassword(testPassword)
        val retrieved = securePreferences.getPassword()
        assertEquals(testPassword, retrieved)
    }

    @Test
    fun clearPassword_removesPassword() {
        securePreferences.savePassword(testPassword)
        securePreferences.clearPassword()
        val retrieved = securePreferences.getPassword()
        assertNull(retrieved)
    }
}