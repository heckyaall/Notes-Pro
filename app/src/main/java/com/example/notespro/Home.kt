package com.example.notespro

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController, noteViewModel: NoteViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val topAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color(0xFFFFDBDB),
        scrolledContainerColor = Color(0xFFFEB5B5)
    )

    var isSearchActive by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val searchResults by noteViewModel.searchResults.collectAsState()
    val allNotes by noteViewModel.notes.collectAsState()

    ModalNavigationDrawer(
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Menu",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                NavigationDrawerItem(
                    label = { Text(text = "Home", fontSize = 15.sp) },
                    selected = true,
                    onClick = { coroutineScope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Outlined.Home, contentDescription = "Home", Modifier.size(27.dp)) }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Column {
                    CenterAlignedTopAppBar(
                        title = { Text(text = "Notes Pro") },
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch { drawerState.open() }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        },
                        scrollBehavior = scrollBehavior,
                        colors = topAppBarColors,
                        actions = {
                            IconButton(onClick = {
                                isSearchActive = !isSearchActive
                                if (!isSearchActive) searchText = "" // Reset search when toggled off
                            }) {
                                Icon(
                                    imageVector = if (isSearchActive) Icons.Default.Close else Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            }
                        }
                    )
                    AnimatedVisibility(
                        visible = isSearchActive,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        TextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                                noteViewModel.search(it)
                            },
                            placeholder = { Text("Search...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White),
                            singleLine = true
                        )
                    }
                }
            },
            floatingActionButton = {
                Fab(
                    onCLick = { navController.navigate(Add.Route) },
                    { Icon(Icons.Filled.Add, contentDescription = "Add Note") },
                    "Add Note",
                    Color(0xFFFEB5B5)
                )
            }
        ) { paddingValues ->
            val notesToDisplay = if (isSearchActive && searchText.isNotBlank()) {
                searchResults
            } else {
                allNotes
            }

            All_notes(
                modifier = Modifier.padding(paddingValues),
                notes = notesToDisplay,
                viewModel = noteViewModel,
                navHostController = navController
            )
        }
    }
}


@Composable
fun Fab(onCLick: () -> Unit = {}, icon: @Composable () -> Unit, text: String, color: Color) {
    ExtendedFloatingActionButton(
        onClick = { onCLick() },
        icon = { icon() },
        text = { Text(text = text) },
        containerColor = color
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun All_notes(
    modifier: Modifier,
    notes: List<Note>,
    viewModel: NoteViewModel,
    navHostController: NavHostController
) {
    var noteToDelete by remember { mutableStateOf<Note?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LazyColumn(modifier = modifier.fillMaxSize().padding(top = 10.dp)) {
        itemsIndexed(notes) { index, note ->
            val backgroundColor = listOf(
                Color(0xFFFFCDD2), Color(0xFFC8E6C9), Color(0xFFBBDEFB),
                Color(0xFFFFF9C4), Color(0xFFD1C4E9), Color(0xFFFFE0B2),
                Color(0xFFE1BEE7), Color(0xFFB3E5FC), Color(0xFFD7CCC8)
            )[index % 9]

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                    .padding(10.dp)
                    .combinedClickable(
                        onClick = { navHostController.navigate("details/${note.id}") },
                        onLongClick = {
                            noteToDelete = note
                            showDeleteDialog = true
                        }
                    )
            ) {
                Text(
                    text = note.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = note.content,
                    fontSize = 15.sp,
                    color = Color.Gray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    if (showDeleteDialog && noteToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Note") },
            text = { Text("Are you sure you want to delete this note?") },
            confirmButton = {
                TextButton(onClick = {
                    noteToDelete?.let { viewModel.deleteNoteById(it.id) }
                    showDeleteDialog = false
                }) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
