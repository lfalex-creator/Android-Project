package com.example.androidapp.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidapp.R
import com.example.androidapp.viewModels.TTTViewModel

@Composable
fun TicTacToeScreen(viewModel: TTTViewModel = viewModel()) {
    viewModel.context = LocalContext.current
    var matr by remember {mutableStateOf(viewModel.matr)}
    viewModel.SymbolDialog()
    DisposableEffect(Unit) {
        onDispose {
            viewModel.matr= mutableStateListOf(
                mutableStateListOf(' ', ' ', ' '),
                mutableStateListOf(' ', ' ', ' '),
                mutableStateListOf(' ', ' ', ' ')
            )
        }
    }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(3) { row ->
                Row {
                    repeat(3) { col ->
                        Cell(
                            row = row,
                            col = col,
                            onMove = {viewModel.onMove(row,col)},
                            viewModel = viewModel
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = if (viewModel.playerOneTurn) stringResource(R.string.TTT_p1t) else stringResource(R.string.TTT_p2t) ,
                fontSize = 20.sp,
                color = if (viewModel.playerOneTurn) viewModel.playerOneColour else viewModel.playerTwoColour,
                textAlign = TextAlign.Center
            )
        }
    }

@Composable
fun Cell(
    row: Int,
    col: Int,
    onMove: () -> Unit,
    viewModel: TTTViewModel
) {
    var currentColour by remember { mutableStateOf(Color.White) }

    Box(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .clickable {
                if (viewModel.matr[row][col] == ' ')
                {
                    currentColour = if (viewModel.playerOneTurn) viewModel.playerOneColour else viewModel.playerTwoColour
                    onMove()
                }

            }
            .size(110.dp, 110.dp),
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = (viewModel.matr[row][col]).toString(),
            modifier = Modifier.fillMaxSize(),
            fontSize = 100.sp,
            color = currentColour,
            textAlign = TextAlign.Center
        )
    }
}

    @Preview(showBackground = true)
    @Composable
    fun TicTacToeScreenPreview() {
        TicTacToeScreen()
    }