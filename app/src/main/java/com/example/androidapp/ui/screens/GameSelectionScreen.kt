package com.example.androidapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GameSelectionScreen(
    goToTicTacToe:()->Unit = {},
    logout: ()->Unit = {}
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
                Text(text="TicTacToe")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Text(text="Placeholder2")
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .height(50.dp),
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(
                text="Placeholder3"
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text="Placeholder4"
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick=logout)
        {
            Text(text = "Log out")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GameSelectionScreenPreview() {
    GameSelectionScreen()
}