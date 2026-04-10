package com.example.androidapp.ui.screens

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext

    @Composable
    fun TicTacToeScreen() {
        var playerOneTurn by remember { mutableStateOf(true) }
        val context=LocalContext.current
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(3) {
                Row {
                    repeat(3) {
                        Cell(
                            playerOneTurn = playerOneTurn,
                            onMove = {
                                playerOneTurn = !playerOneTurn
                                Turn(context)
                            }
                        )
                    }
                }
            }
        }
    }
    fun Turn(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Alert")
            .setMessage("Hello!")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    @Composable
    fun Cell(
        playerOneTurn: Boolean,
        onMove: () -> Unit
    ) {
        var text by remember { mutableStateOf("") }
        var currentColour by remember { mutableStateOf(Color.White) }
        Box(
            modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(40.dp)
                .clickable {
                    if (text == "") {
                        text = if (playerOneTurn) "X" else "O"
                        currentColour = if (playerOneTurn) Color.Red else Color.Blue
                        onMove()
                    }
                },
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = text,
                modifier = Modifier.size(40.dp),
                fontSize = 35.sp,
                color = currentColour
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun TicTacToeScreenPreview() {
        TicTacToeScreen()
    }