package com.example.androidapp.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidapp.viewModels.BSViewModel

@Composable
fun BallSortingScreen(viewModel: BSViewModel = viewModel())
{
    val colours = viewModel.generateRandomColours()
    val matrix = viewModel.scrambleSolution(colours)
    var aBallSelected = false
    Column(
        modifier= Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    )
    {
        repeat(4) { row->
            Row(
                modifier= Modifier
                    .padding(15.dp,15.dp),
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                repeat(3){ col->
                    var nr=row*3+col
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(170.dp)
                            .border(5.dp,Color.Red),
                        contentAlignment = Alignment.BottomCenter
                    )
                    {
                        Canvas(
                            modifier = Modifier
                                .width(100.dp)
                                .height(170.dp)
                                .clickable{
                                    var index = -1
                                    for(i in 2 downTo 0)
                                        if(matrix[i][nr]!=-1)
                                        {
                                            index=i
                                            break
                                        }
                                    if(matrix[3][nr]==-1)
                                    {
                                        val aux = matrix[index][nr]
                                        matrix[index][nr]=-1
                                        matrix[3][nr]=aux
                                    }
                                    else
                                    {
                                        matrix[index+1][nr]=matrix[3][nr]
                                        matrix[3][nr]=-1
                                    }
                                }
                        )
                        {
                            drawPath(
                                path=drawCup(40f,90f),
                                color = Color.Blue,
                                style = Stroke(width = 6f)
                            )
                            if(matrix[3][nr]!=-1)
                                drawCircle(
                                    color= colours.getValue(matrix[3][nr]),
                                    radius = 25.0f,
                                    center = Offset(90f,60f)
                                )
                            if(matrix[2][nr]!=-1)
                                drawCircle(
                                    color= colours.getValue(matrix[2][nr]),
                                    radius = 25.0f,
                                    center = Offset(90f,120f)
                                )
                            if(matrix[1][nr]!=-1)
                                drawCircle(
                                    color= colours.getValue(matrix[1][nr]),
                                    radius = 25.0f,
                                    center = Offset(90f,180f)
                                )
                            if(matrix[0][nr]!=-1)
                                drawCircle(
                                    color= colours.getValue(matrix[0][nr]),
                                    radius = 25.0f,
                                    center = Offset(90f,240f)
                                )
                        }
                    }
                }
            }
        }
    }
}
fun drawCup(x:Float, y:Float): Path
{
    return Path().apply {
        moveTo(x, y)
        lineTo(x, y+150)
        arcTo(
            rect = Rect(x, y+100, x+100, y+200),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 180f,
            forceMoveTo = true
        )
        moveTo(x+100, y+150)
        lineTo(x+100, y)
    }
}