package com.example.takeanote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_notes")
data class Note(
    @PrimaryKey(autoGenerate = true) var nId : Int = 0,
    @ColumnInfo(name = "header")var header : String,
    @ColumnInfo(name = "text")var text : String
)
