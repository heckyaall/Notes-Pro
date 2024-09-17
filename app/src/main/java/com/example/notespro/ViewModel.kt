package com.example.notespro

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun addOrUpdate(note: Note) = noteDao.addOrUpdate(note)

    suspend fun delete(note: Note) = noteDao.delete(note)

    fun searchNotes(query: String): Flow<List<Note>> = noteDao.searchNotes(query)
}

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private val _searchResults = MutableStateFlow<List<Note>>(emptyList())
    val searchResults: StateFlow<List<Note>> = _searchResults

    init {
        viewModelScope.launch {
            repository.getAllNotes().collect { notes ->
                _notes.value = notes
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            repository.searchNotes(query).collect { results ->
                _searchResults.value = results
            }
        }
    }

    fun addOrUpdate(note: Note) = viewModelScope.launch {
        repository.addOrUpdate(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
}