package com.example.androidapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidapp.ui.data.entities.UserEntity
import com.example.androidapp.ui.screens.BallSortingScreen
import com.example.androidapp.ui.screens.ColourPickerScreen
import com.example.androidapp.ui.screens.GameSelectionScreen
import com.example.androidapp.ui.screens.MineSweeperScreen
import com.example.androidapp.ui.screens.TicTacToeScreen
import com.example.androidapp.ui.screens.UsersListScreen
import com.example.androidapp.ui.screens.UsersScreen
import com.example.androidapp.util.PrefsHelper
import com.example.androidapp.viewModels.NetworkViewModel
import com.example.androidapp.viewModels.UsersViewModel

@Composable
fun GameSelectionNavigation(
    onLogout: ()->Unit
){
    val navController=rememberNavController()
    val networkViewModel: NetworkViewModel = viewModel()
    val usersViewModel : UsersViewModel = viewModel()
    val localUsers by usersViewModel.users.collectAsState()
    val context = LocalContext.current
    val currentUserEmail = PrefsHelper.getLastEmail(context)
    val currentUser = localUsers.find { it.email == currentUserEmail } ?: null
    NavHost (
        navController= navController,
        startDestination = "gameSelection"
    ){
        composable("gameSelection") {
            GameSelectionScreen(
                goToTicTacToe = { navController.navigate("TTTScreen") },
                goToBallSorting = {navController.navigate("BSScreen")},
                goToColourPicker = {navController.navigate("CPScreen")},
                goToMinesweeper = {navController.navigate("MSScreen")},
                goToUsers = {navController.navigate("UsersScreen")},
                goToNetworkList = {navController.navigate("NetworkUsersScreen")},
                logout = onLogout
            )
        }

        composable("TTTScreen") {
            TicTacToeScreen()
        }
        composable("BSScreen"){
            BallSortingScreen(currentUser = currentUser)
        }
        composable("CPScreen") {
            ColourPickerScreen(currentUser=currentUser)
        }
        composable("MSScreen") {
            MineSweeperScreen(currentUser = currentUser)
        }
        composable("UsersScreen"){
            UsersScreen()
        }
        composable("NetworkUsersScreen") {
            UsersListScreen(viewModel = networkViewModel,currentUser = currentUser)
        }
    }

}
