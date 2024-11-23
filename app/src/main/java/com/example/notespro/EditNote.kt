package com.example.notespro

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditNoteScreen(
    noteId: Int,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    viewModel: NoteViewModel
) {
    val background by remember { mutableStateOf(noteColors.random()) }
    // Retrieve the note details from the ViewModel
    val note by viewModel.getNoteById(noteId).collectAsState(initial = null)

    // Use remember to initialize the title and content only once
    val initialTitle = note?.title ?: ""
    val initialContent = note?.content ?: ""

    var title by remember(note) { mutableStateOf(initialTitle) }
    var content by remember(note) { mutableStateOf(initialContent) }

    Scaffold(
        modifier = Modifier.background(background),
        topBar = {
            TopAppBar(
                title = { Text("Edit Note") },
                navigationIcon = {
                    IconButton(onClick = { onCancelClick() }) {
                        Icon(Icons.Default.Close, contentDescription = "Cancel")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.updateNote(
                            Note(
                                id = noteId,
                                title = title,
                                content = content
                            )
                        )
                        onSaveClick()
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(background)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.background(background)) {
            if (note != null) {
                EditNoteContent(
                    title = title,
                    onTitleChange = { title = it },
                    content = content,
                    onContentChange = { content = it },
                    modifier = Modifier.padding(padding),
                    color = background
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

    }
}

@Composable
fun EditNoteContent(
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    color: Color
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color)
    ) {
        BasicTextField(
            value = title,
            onValueChange = onTitleChange,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            ),
            decorationBox = { innerTextField ->
                if (title == "") {
                    Text("Title", style = TextStyle(color = Color.Black, fontSize = 30.sp))
                }
                innerTextField()
            },
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        )
        BasicTextField(
            value = content,
            onValueChange = onContentChange,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            decorationBox = { innerTextField ->
                if (content == " ") {
                    Text("Content", style = TextStyle(color = Color.Black, fontSize = 30.sp))
                }
                innerTextField()
            },
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        )
    }
}
