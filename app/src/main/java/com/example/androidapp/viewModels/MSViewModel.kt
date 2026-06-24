package com.example.androidapp.viewModels

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.androidapp.R

class MSViewModel : ViewModel() {

    class Cell(var isMine: Boolean = false, var isRevealed: Boolean = false, var isFlagged: Boolean = false, var adjacentMines: Int = 0)
    var mineField = MutableList(10) { MutableList(10) { Cell() } }

    var revealedCells = 0
    var currentMines = 0
    var flagMode = false;
    fun initializeField() {
        // Reset the minefield
        for (i in 0 until 10) {
            for (j in 0 until 10) {
                mineField[i][j] = Cell()
            }
        }
        revealedCells = 0
        currentMines = 10
        var minesPlaced = 0
        while (minesPlaced < currentMines) {
            val row = (0 until 10).random()
            val col = (0 until 10).random()
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
        if(flagMode) {
            if (!mineField[row][col].isRevealed) {
                mineField[row][col].isFlagged = !mineField[row][col].isFlagged
                currentMines += if (mineField[row][col].isFlagged) -1 else 1
            }
            return
        }
        if (mineField[row][col].isFlagged || mineField[row][col].isRevealed) return

        mineField[row][col].isRevealed = true
        revealedCells++

        if (mineField[row][col].isMine) {
            gameOver(context)
        } else if (revealedCells == 100 - currentMines) {
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
        if(flagMode){
            if (mineField[row][col].isFlagged || mineField[row][col].isRevealed) return

            mineField[row][col].isRevealed = true

            if (mineField[row][col].isMine) {
                gameOver(context)
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


        AlertDialog.Builder(context)
            .setMessage("Game Over! You hit a mine.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
        initializeField()
    }

    fun win(context: Context){
        AlertDialog.Builder(context)
            .setMessage("Congratulations! You've cleared the minefield.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
        initializeField()
    }
}