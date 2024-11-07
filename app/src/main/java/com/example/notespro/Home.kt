package com.example.notespro

import android.content.ClipData
import android.media.RouteListingPreference.Item
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.Flow
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

    ModalNavigationDrawer(modifier = Modifier.fillMaxSize(), drawerState = drawerState, drawerContent = {
        ModalDrawerSheet {
            Text("Menu", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            HorizontalDivider(modifier = Modifier.padding(top = 3.dp, bottom = 17.dp))
            NavigationDrawerItem(
                label = { Text(text = "Home", fontSize = 15.sp) },
                selected = true,
                onClick = { coroutineScope.launch { drawerState.close()} },
                icon = { Icon(Icons.Outlined.Home, contentDescription = "Home", Modifier.size(27.dp)) }
            )
        }
    }) {
        Scaffold(modifier = Modifier
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
                            onValueChange = { searchText = it },
                            placeholder = { Text("Search...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .offset(y = 8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White),
                            singleLine = true
                        )
                    }
                }
            },
            floatingActionButton = { Fab(onCLick = { navController.navigate(Add.Route)}, {Icon(Icons.Filled.Add, contentDescription = "Add Note")}, "Add Note", Color(0xFFFEB5B5))}
        ) { paddingValues ->

            All_notes(Modifier.padding(paddingValues), viewModel = noteViewModel)
        }
    }
}

@Composable
fun Fab(onCLick: () -> Unit = {}, icon: @Composable () -> Unit, text : String, color : Color) {
    ExtendedFloatingActionButton(
        onClick = { onCLick() },
        icon = { icon() },
        text = { Text(text = text) },
        containerColor = color
    )
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun All_notes(modifier : Modifier, viewModel: NoteViewModel) {
    val all by viewModel.notesFlow.collectAsState(initial = emptyList())
    LazyColumn (modifier =  modifier) {
        items(all){note ->
            Text("Title: ${note.first}")
            Text( "Content: ${note.second}")
        }
    }
}