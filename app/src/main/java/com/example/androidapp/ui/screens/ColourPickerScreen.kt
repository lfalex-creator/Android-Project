package com.example.androidapp.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidapp.ui.data.entities.UserEntity
import com.example.androidapp.viewModels.CPViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@Composable
fun ColourPickerScreen(
    viewModel: CPViewModel = viewModel(),
    currentUser: UserEntity?) {
    val matrix = viewModel.matrix
    viewModel.currentUser=currentUser ?: UserEntity(0,"")
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text("Level: "+viewModel.level.toString())
        repeat(10)
        {row->
            Row()
            {
                repeat (10)
                {col->
                    if(matrix[row][col])
                        Cell(viewModel.colourToPick,viewModel::onClick,row,col)
                    else
                        Cell(viewModel.colour,viewModel::onClick,row,col)
                }
            }
        }
    }

}
@Composable
fun Cell(colour: Color, onClick:(Int, Int, Context)->Unit, row:Int, col:Int) {
    val context=LocalContext.current
    Box(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .size(36.dp, 36.dp)
            .background(colour)
            .clickable{
                onClick(row,col,context)
            },
        contentAlignment = Alignment.Center
    ){}
}