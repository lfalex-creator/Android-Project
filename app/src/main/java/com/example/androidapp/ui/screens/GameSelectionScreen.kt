package com.example.androidapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidapp.R

@Composable
fun GameSelectionScreen(
    goToTicTacToe:()->Unit = {},
    goToBallSorting:()->Unit = {},
    goToColourPicker:()->Unit = {},
    goToMinesweeper:()->Unit = {},
    logout: ()->Unit = {},
    goToProfile: () -> Unit = {},
    goToNetworkList: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row(
            modifier = Modifier
                .height(50.dp),
            horizontalArrangement = Arrangement.Center
        )
        {
            Button(onClick=goToTicTacToe)
            {
                Text(text=stringResource(R.string.TTT))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick=goToBallSorting)
            {
                Text(text=stringResource(R.string.BS))
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .height(50.dp),
            horizontalArrangement = Arrangement.Center
        )
        {
            Button(onClick=goToColourPicker)
            {
                Text(text=stringResource(R.string.CP))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick=goToMinesweeper)
            {
                Text(text = stringResource(R.string.MS))
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick=goToProfile)
        {
            Text(text=stringResource(R.string.profile))
        }
        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = goToNetworkList)
        {
            Text(text = stringResource(R.string.leaderboard))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick=logout)
        {
            Text(text = stringResource(R.string.logout))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameSelectionScreenPreview() {
    GameSelectionScreen()
}