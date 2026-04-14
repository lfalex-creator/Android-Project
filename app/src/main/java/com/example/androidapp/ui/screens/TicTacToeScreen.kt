package com.example.androidapp.ui.screens

import android.R
import android.app.AlertDialog
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


var matr =arrayOf(
    charArrayOf(' ', ' ', ' '),
    charArrayOf(' ', ' ', ' '),
    charArrayOf(' ', ' ', ' ')
)

@Composable
    fun TicTacToeScreen() {
        var playerOneTurn by remember { mutableStateOf(true) }
        val context=LocalContext.current


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
                                matr[row][col] = if (playerOneTurn) 'X' else 'O'
                                if(CheckWinner(matr))
                                {
                                    ShowWinner(context, playerOneTurn)
                                    Reset(matr)
                                }
                                playerOneTurn = !playerOneTurn
                            }
                        )
                    }
                }
            }
        }
    }
    fun ShowWinner(context: Context,playerOneTurn: Boolean) {
        AlertDialog.Builder(context)
            .setTitle("Winner!")
            .setMessage(if (playerOneTurn) "Player 1 wins!" else "Player 2 wins!")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
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
                        currentColour = if (playerOneTurn) Color.Red else Color.Blue
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