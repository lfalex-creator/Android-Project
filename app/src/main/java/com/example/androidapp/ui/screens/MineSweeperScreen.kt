package com.example.androidapp.ui.screens

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidapp.ui.data.entities.UserEntity
import com.example.androidapp.viewModels.MSViewModel

@Composable
fun MineSweeperScreen(viewModel: MSViewModel = viewModel(),currentUser: UserEntity?) {
    val context = LocalContext.current
    viewModel.currentUser=currentUser ?: UserEntity(0,"")
    LaunchedEffect(Unit) {
        viewModel.initializeField()
    }


    Column(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(text = "Mines left: ${viewModel.currentMines}")
        Spacer(modifier = Modifier.height(16.dp))

        repeat(10) { row ->
            Row {
                repeat(10) { col ->
                    val cell = viewModel.mineField[row][col]
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .padding(1.dp)
                            .background(if (cell.isRevealed) Color.LightGray else Color.Gray)
                            .border(1.dp, Color.DarkGray)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        viewModel.onClick(row, col, context)
                                    },
                                    onLongPress = {
                                        viewModel.holdClick(row, col, context)
                                    }
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (cell.isRevealed) {
                            if (cell.isMine) {
                                Text(text = "💣")
                            } else if (cell.adjacentMines > 0) {
                                Text(text = cell.adjacentMines.toString())
                            }
                        } else if (cell.isFlagged) {
                            Text(text = "🚩")
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            viewModel.toggleFlagMode()
        }) {
            Text(text = if (viewModel.flagMode) "🚩" else "💣")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.disableTap = false
            viewModel.initializeField()
        }) {
            Text(text = "Restart Game")
        }
    }


}