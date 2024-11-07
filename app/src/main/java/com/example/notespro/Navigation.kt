package com.example.notespro

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController, viewModel: NoteViewModel){
    NavHost(startDestination = Home.Route, navController = navController){
        composable(Home.Route){
            Home(navController, viewModel)
        }
        composable(ViewNote.Route){
            Notes()
        }
        composable(Add.Route){
            AddNote(viewModel, navController)
        }
        composable(Edit.Route){
            EditNote()
        }
    }
}