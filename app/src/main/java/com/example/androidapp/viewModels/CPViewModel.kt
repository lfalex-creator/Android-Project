package com.example.androidapp.viewModels

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapp.R
import com.example.androidapp.ui.data.AppDataBase
import com.example.androidapp.ui.data.entities.UserEntity
import com.example.androidapp.ui.data.entities.UserGameEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.random.Random

class CPViewModel(
    application: Application
) : AndroidViewModel(application) {
    var matrix = List(10) {
        mutableStateListOf<Boolean>()
    }
    var level = 0
    var currentDifference = 0.58f
    var colour = Color(0)
    var colourToPick = Color(0)
    lateinit var currentUser : UserEntity
    private val userDao = AppDataBase.getDatabase(application).userDao()
    private val usersGamesDao = AppDataBase.getDatabase(application).usersGamesDao()

    init
    {
        for(i in 0..9)
            for(j in 0..9)
                matrix[i].add(false)
        prepareNewMatrix()
    }
    fun generateRandomColours()
    {
        val r = Random.nextFloat()
        val g = Random.nextFloat()
        val b= Random.nextFloat()

        colour = Color(r,g,b,colorSpace = ColorSpaces.Srgb)

        val dr = Random.nextFloat()*currentDifference
        val dg = Random.nextFloat()*(currentDifference-dr)
        val db = Random.nextFloat()*(currentDifference-dr-dg)

        colourToPick = Color(r-dr,g-dg,b-db,colorSpace = ColorSpaces.Srgb)
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

        currentDifference-=0.058f
        level++
    }
    fun onClick(row:Int, col:Int, context:Context)
    {
        if(matrix[row][col])
        {
            if(currentDifference>0.001f)
                prepareNewMatrix()
            else
                win(context)
        }
        else
            lose(context)
    }
    fun win(context: Context)
    {
        viewModelScope.launch {
            val games = userDao.getGamesOfUser(currentUser.id).first()

            val thisGame = games.first { it.gameId == 2L }

            usersGamesDao.addGame(
                UserGameEntity(
                    userId = currentUser.id,
                    gameId = thisGame.gameId,
                    score = thisGame.score + 20
                )
            )
        }
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
        viewModelScope.launch {
            val games = userDao.getGamesOfUser(currentUser.id).first()

            val thisGame = games.first { it.gameId == 2L }

            usersGamesDao.addGame(
                UserGameEntity(
                    userId = currentUser.id,
                    gameId = thisGame.gameId,
                    score = thisGame.score - 5
                )
            )
        }
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
        currentDifference = 0.58f
        prepareNewMatrix()
    }
}