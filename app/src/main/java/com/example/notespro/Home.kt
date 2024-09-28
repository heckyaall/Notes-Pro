package com.example.notespro

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
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
                onClick = { /*TODO*/ },
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
            floatingActionButton = { Fab() }
        ) { paddingValues ->
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {

            }
        }
    }
}

@Composable
fun Fab() {
    ExtendedFloatingActionButton(
        onClick = {  },
        icon = { Icon(Icons.Filled.Add, contentDescription = "Add Note") },
        text = { Text(text = "Add Note") },
        containerColor = Color(0xFFFEB5B5)
    )
}
