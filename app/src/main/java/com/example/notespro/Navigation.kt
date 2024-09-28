package com.example.notespro

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(context : Context, navController: NavHostController){
    NavHost(startDestination = Home.Route, navController = navController){
        composable(Home.Route){
            Home()
        }
        composable(ViewNote.Route){
            Notes()
        }
        composable(Add.Route){
            AddNote()
        }
        composable(Edit.Route){
            EditNote()
        }
    }
}