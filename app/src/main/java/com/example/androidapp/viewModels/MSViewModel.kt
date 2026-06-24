package com.example.androidapp.viewModels

import android.app.AlertDialog
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androidapp.R
import kotlin.collections.get

class MSViewModel : ViewModel() {

    class Cell {
        var isMine by mutableStateOf(false)
        var isRevealed by mutableStateOf(false)
        var isFlagged by mutableStateOf(false)
        var adjacentMines by mutableIntStateOf(0)
    }
    var mineField = MutableList(10) { MutableList(10) { Cell() } }

    var isInitialized by mutableStateOf(false)
    var revealedCells by mutableIntStateOf(0)
    var currentMines = 0
    var flagMode by mutableStateOf(false)
    var disableTap by mutableStateOf(false)
    fun initializeField(tappedRow: Int = -1, tappedCol: Int = -1) {

        if (tappedRow == -1 || tappedCol == -1) {
            isInitialized = false
            revealedCells = 0
            currentMines = 10
            for (i in 0 until 10) {
                for (j in 0 until 10) {
                    mineField[i][j].apply {
                        isMine = false
                        isRevealed = false
                        isFlagged = false
                        adjacentMines = 0
                    }
                }
            }
            return
        }

        isInitialized = true
        for (i in 0 until 10) {
            for (j in 0 until 10) {
                mineField[i][j].apply {
                    isMine = false
                    isRevealed = false
                    isFlagged = false
                    adjacentMines = 0
                }
            }
        }
        revealedCells = 0
        currentMines = 10
        var minesPlaced = 0
        while (minesPlaced < currentMines) {
            val row = (0 until 10).random()
            val col = (0 until 10).random()
            if(row == tappedRow && col == tappedCol) continue
            if (!mineField[row][col].isMine) {
                mineField[row][col].isMine = true
                minesPlaced++
            }
        }

        for (i in 0 until 10) {
            for (j in 0 until 10) {
                if (!mineField[i][j].isMine) {
                    mineField[i][j].adjacentMines = countAdjacentMines(i, j)
                }
            }
        }
    }

    private fun countAdjacentMines(row: Int, col: Int): Int {
        var count = 0
        for (i in -1..1) {
            for (j in -1..1) {
                val newRow = row + i
                val newCol = col + j
                if (newRow in 0 until 10 && newCol in 0 until 10 && mineField[newRow][newCol].isMine) {
                    count++
                }
            }
        }
        return count
    }

    fun onClick(row: Int, col: Int, context: Context) {
        if(disableTap) return
        if(flagMode) {
            if (!mineField[row][col].isRevealed) {
                mineField[row][col].isFlagged = !mineField[row][col].isFlagged
                currentMines += if (mineField[row][col].isFlagged) -1 else 1
            }
            return
        }

        if(!isInitialized) {
            initializeField(row, col)
        }

        if (mineField[row][col].isFlagged || mineField[row][col].isRevealed) return

        mineField[row][col].isRevealed = true
        revealedCells++

        if (mineField[row][col].isMine) {
            gameOver(context)
        } else if (revealedCells == 90) {
            win(context)
        } else if (mineField[row][col].adjacentMines == 0) {
            for (i in -1..1) {
                for (j in -1..1) {
                    val newRow = row + i
                    val newCol = col + j
                    if (newRow in 0 until 10 && newCol in 0 until 10) {
                        onClick(newRow, newCol, context)
                    }
                }
            }
        }
    }

    fun holdClick(row: Int, col: Int, context: Context) {
        if(disableTap) return
        if(flagMode){
            if(!isInitialized) {
                initializeField(row, col)
            }

            if (mineField[row][col].isFlagged || mineField[row][col].isRevealed) return

            mineField[row][col].isRevealed = true
            revealedCells++

            if (mineField[row][col].isMine) {
                gameOver(context)
            } else if (revealedCells == 90) {
                win(context)
            } else if (mineField[row][col].adjacentMines == 0) {
                for (i in -1..1) {
                    for (j in -1..1) {
                        val newRow = row + i
                        val newCol = col + j
                        if (newRow in 0 until 10 && newCol in 0 until 10) {
                            holdClick(newRow, newCol, context)
                        }
                    }
                }
            }
            return
        }
        if (!mineField[row][col].isRevealed) {
            mineField[row][col].isFlagged = !mineField[row][col].isFlagged
            currentMines += if (mineField[row][col].isFlagged) -1 else 1
        }
    }

    fun toggleFlagMode() {
        flagMode = !flagMode
    }

    fun gameOver(context: Context){
        isInitialized = false
        disableTap = true
        for (i in 0 until 10) {
            for (j in 0 until 10) {
                if (mineField[i][j].isMine) {
                    mineField[i][j].isRevealed = true
                }
            }
        }
        AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.MS_lose))
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
//        initializeField()
    }

    fun win(context: Context){
        isInitialized = false
        disableTap = true
        AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.MS_win))
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
//        initializeField()
    }
}