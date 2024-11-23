package com.example.notespro

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.Color

val LightGra = Color(0xD38EFFF0)
val LightBlu = Color(0xADD8E6FF)
val LightGree = Color(0x90EE90FF)
val LightYello = Color(0xFF8BC34A)
val LightPin = Color(0xFF4CAF50)
val LightPurpl = Color(0xFFFEB5B5)
val LightOrang = Color(0xFFFF9800)
val Beig = Color(0xFF50A5F3)
val Ivor = Color(0xFFFFC107)
val MintGree = Color(0xFFEC5584)
val Lavende = Color(0xE0C0E0FF)
val LemonYello = Color(0xFFFFFF83)

val noteColor = listOf(
    LightGra,
    LightBlu,
    LightGree,
    LightYello,
    LightPin,
    LightPurpl,
    LightOrang,
    Beig,
    Ivor,
    MintGree,
    Lavende,
    LemonYello
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Notes(
    noteId: Int,
    navController: NavController,
    viewModel: NoteViewModel
) {
    // Remember a random color for consistent background until the composable recomposes
    val color by remember { mutableStateOf(noteColor.random()) }

    // Retrieve the note details from the ViewModel
    val note by viewModel.getNoteById(noteId).collectAsState(initial = null)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color) // Apply background color to the root container
    ) {
        Scaffold(
            floatingActionButton = {
                Fab(
                    onCLick = { navController.navigate("editNote/$noteId") },
                    icon = { Icon(Icons.Filled.Edit, contentDescription = "Edit Note") },
                    text = "Edit Note",
                    color = color
                )
            },
            topBar = {
                TopAppBar(
                    title = { Text("Note Details") },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(Home.Route) }) {
                            Icon(Icons.Default.Close, contentDescription = "Cancel")
                        }},
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(color)
                )
            }
        ) { padding ->
            if (note != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(color)
                ) {
                    Text(
                        text = note!!.title,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = note!!.content,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}