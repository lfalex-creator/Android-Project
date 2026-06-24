package com.example.androidapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidapp.ui.screens.RegisterScreen
import com.example.androidapp.ui.screens.LogInScreen
import com.example.androidapp.viewModels.AuthViewModel

@Composable
fun AuthenticationNavigation(
    authViewModel: AuthViewModel= viewModel()

){
    val navController=rememberNavController()
    val authState by authViewModel.authState.collectAsState()
    val startDestination = "login"
    NavHost (
        navController= navController,
        startDestination = startDestination
    ){
        composable("login") {
            LogInScreen(
                onRegisterClick = {
                    navController.navigate("register")
                },
                onLoginClick = { email, password ->
                    authViewModel.login(email, password)
                },
                isLoading = authState.isLoading,
                errorMessage = authState.errorMessage

            )
        }
        composable("register") {
            RegisterScreen(
                onLoginClick = {
                    navController.popBackStack()
                },
                onRegisterClick = { email, password ->
                    authViewModel.register(email, password)
                },
                isLoading = authState.isLoading,
                errorMessage = authState.errorMessage,
                viewModel = authViewModel
            )
        }


    }
}