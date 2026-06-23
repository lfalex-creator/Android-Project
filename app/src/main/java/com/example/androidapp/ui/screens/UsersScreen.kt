package com.example.androidapp.ui.screens

import android.app.Application
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidapp.util.isValidEmail
import com.example.androidapp.util.isValidPassword
import com.example.androidapp.viewModels.UsersViewModel
import kotlin.text.insert
import com.example.androidapp.ui.data.entities.UserEntity
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.androidapp.R
import com.example.androidapp.ui.data.entities.GamesDataEntity
import kotlinx.coroutines.flow.Flow

@Composable
fun UsersScreen(viewModel: UsersViewModel = viewModel())
{
    val users = viewModel.users.collectAsState(emptyList())
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .border(width = 5.dp, color = Color.Red),
        verticalArrangement = Arrangement.spacedBy(16.dp), //pune intre fiecare elem cate un spatiu de 16 dp
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(users.value){ user -> //un fel de foreach()
            UserCell(user,viewModel.getUserGames(user.id))
        }
    }
}

@Composable
fun UserCell(user: UserEntity,games: Flow<List<GamesDataEntity>>)
{
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = user.email,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),//f - float, pt a nu crea evidenta pt fiecare culoare
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(5.dp))
        val games by games.collectAsState(initial = emptyList())
        val totalScore= games.sumOf { gamesDataEntity -> gamesDataEntity.score }
            Row(
                horizontalArrangement = Arrangement.Center
            )
            {
                Text(
                    text = "Scor:",
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),//f - float, pt a nu crea evidenta pt fiecare culoare
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = totalScore.toString(),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),//f - float, pt a nu crea evidenta pt fiecare culoare
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        Spacer(modifier = Modifier.width(15.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun UsersScreenPreview()
{
    UsersScreen()
}