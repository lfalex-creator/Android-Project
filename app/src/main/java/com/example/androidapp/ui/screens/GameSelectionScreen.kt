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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidapp.R

@Composable
fun GameSelectionScreen(
    goToTicTacToe:()->Unit = {},
    goToBallSorting:()->Unit = {},
    goToColourPicker:()->Unit = {},
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
                Text(text="Colour Picker")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text="Placeholder4"
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
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