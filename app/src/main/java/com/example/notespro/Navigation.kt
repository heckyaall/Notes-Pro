package com.example.notespro

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun Navigation(navController: NavHostController, viewModel: NoteViewModel) {
    NavHost(startDestination = Home.Route, navController = navController) {

        // Route for Home Screen
        composable(Home.Route) {
            Home(navController = navController, noteViewModel = viewModel)
        }

        // Route for viewing note details
        composable(
            route = "details/{noteId}",
            arguments = listOf(
                navArgument("noteId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId")
            if (noteId != null) {
                Notes(
                    noteId = noteId,
                    navController = navController,
                    viewModel = viewModel
                )
            } else {
                // Fallback if noteId is null
                ErrorScreen("Invalid Note ID")
            }
        }

        // Route for adding a new note
        composable(Add.Route) {
            AddNote(
                viewModel,
                navController
            )
        }

        // Route for editing a note
        composable(
            route = "editNote/{noteId}",
            arguments = listOf(
                navArgument("noteId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId")
            if (noteId != null) {
                EditNoteScreen(
                    noteId = noteId,
                    onSaveClick = { navController.popBackStack() },
                    onCancelClick = { navController.popBackStack() },
                    viewModel = viewModel
                )
            } else {
                // Fallback if noteId is null
                ErrorScreen("Invalid Note ID")
            }
        }
    }
}

@Composable
fun ErrorScreen(message: String) {
    // Simple UI for displaying an error message
    Text(text = message)
}
