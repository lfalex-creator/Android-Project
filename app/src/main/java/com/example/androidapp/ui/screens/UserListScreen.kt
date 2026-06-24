package com.example.androidapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidapp.ui.data.entities.UserEntity
import com.example.androidapp.util.PrefsHelper
import com.example.androidapp.viewModels.NetworkViewModel
import com.example.androidapp.viewModels.UsersViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun UsersListScreen(
    viewModel: NetworkViewModel = viewModel(),
    usersViewModel: UsersViewModel = viewModel(),
    currentUser: UserEntity?
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val gamesFlow = remember(currentUser) {
        currentUser?.let { usersViewModel.getUserGames(it.id) } ?: flowOf(emptyList())
    }

    val userGames by gamesFlow.collectAsState(initial = emptyList())
    val totalScore = userGames.sumOf { it.score }

    LaunchedEffect(Unit) {
        if(state.users.isEmpty()){
        viewModel.loadPosts()
            }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                viewModel.sendUser(
                    email = currentUser?.email ?: "no email",
                    score = totalScore
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Publish My High Score")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let {
            Text(text = "Error: $it")
        }

        LazyColumn {
            items(state.users ?: emptyList()) { user ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Email: ${user.email}")
                        Text(text = "Score: ${user.score}")
                    }
                }
            }
        }
    }
}