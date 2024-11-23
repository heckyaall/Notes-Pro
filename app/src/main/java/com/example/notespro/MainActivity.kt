package com.example.notespro

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val noteDatabase = NoteDatabase.getDatabase(this)
            val noteDao = noteDatabase.noteDao()
            val factory = NotesViewModelFactory(NoteRepository(noteDao))

            val notesViewModel: NoteViewModel = viewModel(factory = factory)
            val navController = rememberNavController()
            Navigation(navController = navController, notesViewModel)
        }
    }
}