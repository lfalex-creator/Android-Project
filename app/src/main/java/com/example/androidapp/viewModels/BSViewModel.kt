package com.example.androidapp.viewModels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class BSViewModel : ViewModel() {
    fun generateRandomColours() : HashMap<Int, Color>
    {
        val toReturn = HashMap<Int, Color>()
        val colours = mutableListOf(
            Color.Black,
            Color.Red,
            Color.Blue,
            Color.Green,
            Color.Yellow,
            Color.Magenta,
            Color.Cyan,
            Color.Gray,
            Color(130,15,160),
            Color(255,110,10),
            Color(147,144,240)
        )
        for(i in 0..11)
        {
            if(i!=4)
            {
                val aux = colours.random()
                colours.remove(aux)
                toReturn[i]=aux
            }
        }
        return toReturn
    }
    fun scrambleSolution(cupColours: HashMap<Int, Color>) : Array<IntArray>
    {
        val matrix = arrayOf(
            IntArray(12),
            IntArray(12),
            IntArray(12),
            IntArray(12)
        )
        for(i in 0..11)
        {
            if(i!=4)
            {
                matrix[0][i]=i
                matrix[1][i]=i
                matrix[2][i]=i
            }
            else
            {
                matrix[0][i]=-1
                matrix[1][i]=-1
                matrix[2][i]=-1
            }
            matrix[3][i]=-1
        }

        var i = 0
        while(i<=99)
        {
            val first = Random.nextInt(0,12)
            val second = Random.nextInt(0,12)
            if(first!=second && matrix[0][first]!=-1 && matrix[2][second]==-1)
            {
                var firstIndex = -1
                var secondIndex = -1
                if(matrix[2][first]!=-1)
                {
                    firstIndex = 2
                }
                else
                {
                    if(matrix[1][first]!=-1)
                    {
                        firstIndex = 1
                    }
                    else
                    {
                        firstIndex = 0
                    }
                }

                if(matrix[0][second]==-1)
                {
                    secondIndex = 0
                }
                else
                {
                    if(matrix[1][second]==-1)
                    {
                        secondIndex = 1
                    }
                    else
                    {
                        secondIndex = 2
                    }
                }
                matrix[secondIndex][second]=matrix[firstIndex][first]
                matrix[firstIndex][first]=-1
                i++
            }
        }
        return matrix
    }
}