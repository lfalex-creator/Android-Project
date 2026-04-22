package com.example.androidapp.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun BallSortingScreen()
{
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val density = LocalDensity.current
    val widthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val heightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    Canvas(
    modifier= Modifier.fillMaxSize()
    )
    {
        var xCoord=widthPx/2 - 300f
        var yCoord=150f
        repeat(12) { nr->

            drawPath(
                path=drawCup(xCoord,yCoord),
                color = Color.Blue,
                style = Stroke(width = 6f)
            )
            xCoord=xCoord+250f;
            if((nr+1)%3==0)
            {
                xCoord=widthPx/2 - 300f
                yCoord=yCoord+300f
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