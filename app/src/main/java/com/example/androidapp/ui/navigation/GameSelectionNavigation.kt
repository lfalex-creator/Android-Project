package com.example.androidapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidapp.ui.screens.BallSortingScreen
import com.example.androidapp.ui.screens.GameSelectionScreen
import com.example.androidapp.ui.screens.TicTacToeScreen

@Composable
fun GameSelectionNavigation(
    onLogout: ()->Unit
){
    val navController=rememberNavController()
    NavHost (
        navController= navController,
        startDestination = "gameSelection"
    ){
        composable("gameSelection") {
            GameSelectionScreen(
                goToTicTacToe = { navController.navigate("TTTScreen") },
                goToBallSorting = {navController.navigate("BSScreen")},
                logout = onLogout
            )
        }

        composable("TTTScreen") {
            TicTacToeScreen()
        }

        composable("BSScreen"){
            BallSortingScreen()
        }

    }

}
