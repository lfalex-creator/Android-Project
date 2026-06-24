package com.example.androidapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidapp.viewModels.MSViewModel

@Composable
fun MineSweeperScreen(viewModel: MSViewModel = viewModel()) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.initializeField()
    }


//    Column(
//        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
//        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
//        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
//    ) {
//        Text(text = "Mines left: ${viewModel.currentMines}")
//        Spacer(modifier = Modifier.height(16.dp))
//
//        repeat(10) { row ->
//            androidx.compose.foundation.layout.Row {
//                repeat(10) { col ->
//
//                }
//            }
//        }
//    })


}