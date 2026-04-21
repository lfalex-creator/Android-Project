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

var matr=arrayOf(
    charArrayOf(' ', ' ', ' '),
    charArrayOf(' ', ' ', ' '),
    charArrayOf(' ', ' ', ' ')
)
val playerOneColour = Color.Red
val playerTwoColour = Color.Blue
var playerOneSymb = ' '
var playerTwoSymb = ' '
@Composable
    fun TicTacToeScreen(viewModel: TTTViewModel = viewModel()) {
        var playerOneTurn by remember { mutableStateOf(true) }
        val context=LocalContext.current
    viewModel.SymbolDialog()
    DisposableEffect(Unit) {
        onDispose {
            matr=arrayOf(
                charArrayOf(' ', ' ', ' '),
                charArrayOf(' ', ' ', ' '),
                charArrayOf(' ', ' ', ' ')
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
                            playerOneTurn = playerOneTurn,
                            onMove = {
                                matr[row][col] = if (playerOneTurn) playerOneSymb else playerTwoSymb
                                if(viewModel.checkWinner(matr))
                                {
                                    viewModel.showWinner(context, playerOneTurn)

                                    val temp= playerOneSymb
                                    playerOneSymb = playerTwoSymb
                                    playerTwoSymb = temp

                                    viewModel.reset(matr)
                                }
                                else if (viewModel.checkTie(matr))
                                {
                                    viewModel.showTie(context)

                                    val temp= playerOneSymb
                                    playerOneSymb = playerTwoSymb
                                    playerTwoSymb = temp

                                    viewModel.reset(matr)
                                }
                                playerOneTurn = !playerOneTurn
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = if (playerOneTurn) stringResource(R.string.TTT_p1t) else stringResource(R.string.TTT_p2t) ,
                fontSize = 20.sp,
                color = if (playerOneTurn) playerOneColour else playerTwoColour,
                textAlign = TextAlign.Center
            )
        }
    }

@Composable
fun Cell(
    row: Int,
    col: Int,
    playerOneTurn: Boolean,
    onMove: () -> Unit
) {
    var currentColour by remember { mutableStateOf(Color.White) }
    Box(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .clickable {
                if (matr[row][col] == ' ') {
                    currentColour = if (playerOneTurn) playerOneColour else playerTwoColour
                    onMove()
                }
            }
            .size(110.dp, 110.dp),
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = (matr[row][col]).toString(),
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