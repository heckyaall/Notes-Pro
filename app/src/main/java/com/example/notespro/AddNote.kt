package com.example.notespro

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.test.core.app.ApplicationProvider.getApplicationContext

val LightGray = Color(0xD38EFFF0)
val LightBlue = Color(0xADD8E6FF)
val LightGreen = Color(0x90EE90FF)
val LightYellow = Color(0xFF8BC34A)
val LightPink = Color(0xFF4CAF50)
val LightPurple = Color(0xFFFEB5B5)
val LightOrange = Color(0xFFFF9800)
val Beige = Color(0xFF50A5F3)
val Ivory = Color(0xFFFFC107)
val MintGreen = Color(0xFFEC5584)
val Lavender = Color(0xE0C0E0FF)
val LemonYellow = Color(0xFFFFFF83)

val noteColors = listOf(
    LightGray,
    LightBlue,
    LightGreen,
    LightYellow,
    LightPink,
    LightPurple,
    LightOrange,
    Beige,
    Ivory,
    MintGreen,
    Lavender,
    LemonYellow
)

@Composable
fun AddNote(noteViewModel: NoteViewModel, navHostController: NavHostController) {
    val context = LocalContext.current
    val color = MintGreen
    val backgroundColor = noteColors.random()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = { Fab({ save(noteViewModel, navHostController, title, content, context) }, { Icon(Icons.Filled.Check, contentDescription = "Add Note") }, "Save", color)}) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(values)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            BasicTextField(
                value = title,
                onValueChange = { title = it },
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

            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = content,
                onValueChange = { content = it },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp
                ),
                decorationBox = { innerTextField ->
                    if (content == "") {
                        Text("Type something...", style = TextStyle(color = Color.Black, fontSize = 18.sp))
                    }
                    innerTextField()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f).padding(20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun save(noteViewModel: NoteViewModel, navHostController: NavHostController, title : String, content : String, context: Context){
    if (title.isNotEmpty() && content.isNotEmpty()){
    noteViewModel.addNote(title = title, content = content)
    navHostController.navigate(Home.Route)}
    else{
        Toast.makeText(context, "None of the fields can be empty!", Toast.LENGTH_LONG).show();

    }
}