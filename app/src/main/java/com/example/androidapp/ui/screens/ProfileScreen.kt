package com.example.androidapp.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import com.example.androidapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidapp.viewModels.UsersViewModel
import com.example.androidapp.ui.data.entities.UserEntity
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.androidapp.ui.data.entities.GamesDataEntity

@Composable
fun ProfileScreen(viewModel: UsersViewModel = viewModel(),currentUser: UserEntity?)
{
    val theUser=currentUser ?: UserEntity(0,"")
    val games by viewModel.getUserGames(theUser.id).collectAsState(initial = emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(35.dp))
        Text(text=stringResource(R.string.profile))
        LazyColumn(
            modifier = Modifier
                .padding(24.dp)
                .border(width = 5.dp, color = Color.Red),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(games) { game ->
                GameCell(game)
            }
        }
    }
}

@Composable
fun GameCell(game: GamesDataEntity)
{

    val strMap = mapOf(
        "BS" to stringResource(R.string.BS),
        "CP" to stringResource(R.string.CP),
        "MS" to stringResource(R.string.MS)
    )
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = strMap[game.name] ?: stringResource(R.string.inv_game),
        )
    }
    Row(
        horizontalArrangement = Arrangement.Center
    )
    {
        Text(
            text = stringResource(R.string.score) + ":",
        )
        Text(
            text = game.score.toString(),
        )
    }
        Spacer(modifier = Modifier.width(15.dp))

}