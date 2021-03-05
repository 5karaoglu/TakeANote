package com.example.takeanote

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1,exportSchema = false)
public abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao() : Dao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                        NoteDatabase::class.java,
                        "database_note").build()
                INSTANCE = instance

                instance
            }
        }
    }
}