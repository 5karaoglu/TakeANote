package com.example.takeanote

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class Repository(private val dao: Dao) {

    val notes:Flow<List<Note>> = dao.getAll()

    @WorkerThread
    suspend fun insert(note: Note) = dao.insert(note)

    @WorkerThread
    suspend fun update(note: Note) = dao.update(note)

    @WorkerThread
    suspend fun delete(note: Note) = dao.delete(note)

    @WorkerThread
    suspend fun deleteAll() = dao.deleteAll()
}