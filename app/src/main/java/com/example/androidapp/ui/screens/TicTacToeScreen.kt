package com.example.androidapp.ui.screens

import android.app.AlertDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


var matr=arrayOf(
    charArrayOf(' ', ' ', ' '),
    charArrayOf(' ', ' ', ' '),
    charArrayOf(' ', ' ', ' ')
)
val playerOneColour = Color.Red
val playerTwoColour = Color.Blue
var playerOneSymb=' '
var playerTwoSymb=' '
@Composable
    fun TicTacToeScreen() {
        var playerOneTurn by remember { mutableStateOf(true) }
        val context=LocalContext.current
    CustomDialogExample()
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
                                if(CheckWinner(matr))
                                {
                                    ShowWinner(context, playerOneTurn,false)

                                    val temp= playerOneSymb
                                    playerOneSymb = playerTwoSymb
                                    playerTwoSymb = temp

                                    Reset(matr)
                                }
                                else if (CheckTie(matr))
                                {
                                    ShowWinner(context, playerOneTurn,true)

                                    val temp= playerOneSymb
                                    playerOneSymb = playerTwoSymb
                                    playerTwoSymb = temp

                                    Reset(matr)
                                }
                                playerOneTurn = !playerOneTurn
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = (if (playerOneTurn) "Player 1's" else "Player 2's") + " turn" ,
                fontSize = 20.sp,
                color = if (playerOneTurn) playerOneColour else playerTwoColour,
                textAlign = TextAlign.Center
            )
        }
    }
    fun ShowWinner(context: Context,playerOneTurn: Boolean, isTie: Boolean) {
        if(!isTie)
        {
            AlertDialog.Builder(context)
                .setTitle("Winner!")
                .setMessage(if (playerOneTurn) "Player 1 wins!" else "Player 2 wins!")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
        else
        {
            AlertDialog.Builder(context)
                .setTitle("Tie!")
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
fun CheckTie(matr: Array<CharArray>) : Boolean
{
    for(i in 0 until 3)
        for(j in 0 until 3)
            if(matr[i][j]==' ')
                return false;

    return !CheckWinner(matr);
}
    fun CheckWinner(matr: Array<CharArray>) : Boolean
    {
        for(i in 0 until 3)
        {
            if(matr[i][0]==matr[i][1] && matr[i][1]==matr[i][2] && matr[i][0]!=' ')
                return true;

            if(matr[0][i]==matr[1][i] && matr[1][i]==matr[2][i] && matr[0][i]!=' ')
                return true;
        }

        if(matr[0][0]==matr[1][1] && matr[1][1]==matr[2][2] && matr[1][1]!=' ')
            return true;

        if(matr[0][2]==matr[1][1] && matr[1][1]==matr[2][0] && matr[1][1]!=' ')
            return true;

        return false;
    }

    fun Reset(matr: Array<CharArray>)
    {
        for(i in 0 until 3)
            for(j in 0 until 3)
                matr[i][j]=' '
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

@Composable
fun CustomDialogExample() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Box(
                modifier = Modifier
                    .background(Color.White)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(
                        text = "Player 1, please pick a symbol",
                        fontSize = 20.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    Row {
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                                .clickable {
                                    playerOneSymb='X'
                                    playerTwoSymb='O'
                                    showDialog=false
                                }
                                .size(110.dp, 110.dp),
                            contentAlignment = Alignment.Center
                        )
                        {
                            Text(
                                text = "X",
                                modifier = Modifier.fillMaxSize(),
                                fontSize = 100.sp,
                                color = playerOneColour,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.width(50.dp))
                        Box(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                                .clickable {
                                    playerOneSymb='O'
                                    playerTwoSymb='X'
                                    showDialog=false
                                }
                                .size(110.dp, 110.dp),
                            contentAlignment = Alignment.Center
                        )
                        {
                            Text(
                                text = "O",
                                modifier = Modifier.fillMaxSize(),
                                fontSize = 100.sp,
                                color = playerOneColour,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))

                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }

            }
        }
    }
}

    @Preview(showBackground = true)
    @Composable
    fun TicTacToeScreenPreview() {
        TicTacToeScreen()
    }