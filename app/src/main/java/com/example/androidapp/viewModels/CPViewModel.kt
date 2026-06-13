package com.example.androidapp.viewModels

import android.app.AlertDialog
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.androidapp.R
import kotlin.random.Random

class CPViewModel : ViewModel() {
    var matrix = List(10) {
        mutableStateListOf<Boolean>()
    }
    var level = 0
    var currentDifference = 200
    var colour = Color(0)
    var colourToPick = Color(0)
    init
    {
        for(i in 0..9)
            for(j in 0..9)
                matrix[i].add(false)
        prepareNewMatrix()
    }
    fun generateRandomColours()
    {
        val r = Random.nextInt(0,256)
        val g = Random.nextInt(0,256-r)
        val b= 256 - r -g

        colour = Color(r,g,b)

        val dr = minOf(r,Random.nextInt(0,currentDifference))
        val dg = minOf(g,Random.nextInt(0,currentDifference-dr))
        val db = minOf(b,Random.nextInt(0,currentDifference-dr-dg))

        colourToPick = Color(r-dr,g-dg,b-db)
    }
    fun generateRandomPosition(): Pair<Int,Int>
    {
        val row = Random.nextInt(0,10)
        val col = Random.nextInt(0,10)
        return Pair(row,col)
    }
    fun prepareNewMatrix()
    {
        generateRandomColours()
        val cords = generateRandomPosition()
        for(i in 0..9)
            for(j in 0..9)
                matrix[i][j]=false
        matrix[cords.first][cords.second] = true

        currentDifference-=20
        level++
    }
    fun onClick(row:Int, col:Int, context:Context)
    {
        if(matrix[row][col])
        {
            if(currentDifference!=0)
                prepareNewMatrix()
            else
                win(context)
        }
        else
            lose(context)
    }
    fun win(context: Context)
    {
        //give player points
        AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.CP_win))
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
        Reset()
    }
    fun lose(context: Context)
    {
        //give player points
        AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.CP_lose))
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
        Reset()
    }
    fun Reset()
    {
        level = 0
        currentDifference = 200
        prepareNewMatrix()
    }
}