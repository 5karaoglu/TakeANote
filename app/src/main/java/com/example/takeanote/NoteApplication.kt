package com.example.takeanote

import android.app.Application

class NoteApplication: Application() {

    val database by lazy { NoteDatabase.getDatabase(this) }
    val repository by lazy { Repository(database.noteDao()) }

}