package com.example.takeanote

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    val notes: LiveData<List<Note>> =repository.notes.asLiveData()

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}
@Suppress("UNCHECKED_CAST")
class CustomViewModelFactory(private val repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}