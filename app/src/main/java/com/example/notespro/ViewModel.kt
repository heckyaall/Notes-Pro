package com.example.notespro

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update


class NoteRepository(private val noteDao: NoteDao) {
    fun getNoteById(noteId: Int): Flow<Note?> {
        return noteDao.getNoteById(noteId)
    }
    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }
    suspend fun deleteNoteById(noteId: Int) {
        noteDao.deleteNoteById(noteId)
    }
    suspend fun addOrUpdate(note: Note) = noteDao.addOrUpdate(note)

    fun searchNotes(query: String): Flow<List<Note>> = noteDao.searchNotes(query)
}

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    fun getNoteById(noteId: Int): Flow<Note?> {
        return repository.getNoteById(noteId)
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note)}
        }
    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val newNote = Note(title = title, content = content)
            repository.addOrUpdate(newNote)
        }
    }

    fun deleteNoteById(noteId: Int) {
        viewModelScope.launch {
            repository.deleteNoteById(noteId)
        }
    }

    init {
        viewModelScope.launch {
            repository.getAllNotes().collect { notes ->
                _notes.value = notes
            }
        }
    }
    private val _notes = MutableStateFlow<List<Note>>(listOf()) // List of all notes
    val notes: StateFlow<List<Note>> get() = _notes

    private val _searchResults = MutableStateFlow<List<Note>>(listOf()) // Search results
    val searchResults: StateFlow<List<Note>> get() = _searchResults

    fun search(query: String) {
        if (query.isBlank()) {
            _searchResults.update { emptyList() }
        } else {
            _searchResults.update {
                _notes.value.filter { it.title.contains(query, ignoreCase = true) }
            }
        }
    }
}
class NotesViewModelFactory(private val noteRepository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(noteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
