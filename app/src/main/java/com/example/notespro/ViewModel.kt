package com.example.notespro

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NoteRepository(private val noteDao: NoteDao) {

    // Fetch all notes as a Flow
    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    // Add or update a note
    suspend fun addOrUpdate(note: Note) = noteDao.addOrUpdate(note)

    // Delete a note
    suspend fun delete(note: Note) = noteDao.delete(note)

    // Search notes by query
    fun searchNotes(query: String): Flow<List<Note>> = noteDao.searchNotes(query)
}

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {


    val notesFlow: Flow<List<Pair<String, String>>> = repository.getAllNotes().map { notes ->
        notes.map { note -> Pair(note.title, note.content) }
    }
    // Add a new note
    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val newNote = Note(title = title, content = content)
            repository.addOrUpdate(newNote)
        }
    }

    // StateFlow to observe notes
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    // StateFlow for search results
    private val _searchResults = MutableStateFlow<List<Note>>(emptyList())
    val searchResults: StateFlow<List<Note>> = _searchResults

    // Collect all notes in the ViewModel's lifecycle
    init {
        viewModelScope.launch {
            repository.getAllNotes().collect { notes ->
                _notes.value = notes
            }
        }
    }

    // Search for notes based on a query
    fun search(query: String) {
        viewModelScope.launch {
            repository.searchNotes(query).collect { results ->
                _searchResults.value = results
            }
        }
    }

    // Add or update a note
    fun addOrUpdate(note: Note) = viewModelScope.launch {
        repository.addOrUpdate(note)
    }

    // Delete a note
    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
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
