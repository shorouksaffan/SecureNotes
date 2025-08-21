package com.example.personalnotesapp.data.local.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.personalnotesapp.data.local.dao.NoteDao
import com.example.personalnotesapp.data.local.database.migrations.MIGRATION_1_2
import com.example.personalnotesapp.data.local.database.migrations.MIGRATION_2_3
import com.example.personalnotesapp.data.local.entity.NoteEntity
import com.example.personalnotesapp.domain.model.Note

@Database(entities = [NoteEntity::class], version = 3, exportSchema = true)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile private var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                    "notes.db"
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
            }
    }
}