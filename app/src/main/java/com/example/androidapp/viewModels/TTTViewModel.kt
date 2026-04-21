package com.example.androidapp.viewModels

import android.R.attr.id
import android.app.AlertDialog
import android.content.Context
import android.provider.Settings.Global.getString
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import com.example.androidapp.R
import com.example.androidapp.ui.screens.playerOneColour
import com.example.androidapp.ui.screens.playerOneSymb
import com.example.androidapp.ui.screens.playerTwoSymb

class TTTViewModel : ViewModel()
{

    fun showWinner(context: Context, playerOneTurn: Boolean) {

            AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.TTT_win))
                .setMessage(if (playerOneTurn) context.getString(R.string.TTT_p1w) else context.getString(R.string.TTT_p2w))
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

    fun showTie(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.TTT_tie))
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    fun checkTie(matr: Array<CharArray>) : Boolean
    {
        for(i in 0 until 3)
            for(j in 0 until 3)
                if(matr[i][j]==' ')
                    return false

        return !checkWinner(matr)
    }
    fun checkWinner(matr: Array<CharArray>) : Boolean
    {
        for(i in 0 until 3)
        {
            if(matr[i][0]==matr[i][1] && matr[i][1]==matr[i][2] && matr[i][0]!=' ')
                return true

            if(matr[0][i]==matr[1][i] && matr[1][i]==matr[2][i] && matr[0][i]!=' ')
                return true
        }

        if(matr[0][0]==matr[1][1] && matr[1][1]==matr[2][2] && matr[1][1]!=' ')
            return true

        if(matr[0][2]==matr[1][1] && matr[1][1]==matr[2][0] && matr[1][1]!=' ')
            return true

        return false
    }

    fun reset(matr: Array<CharArray>)
    {
        for(i in 0 until 3)
            for(j in 0 until 3)
                matr[i][j]=' '
    }


    @Composable
    fun SymbolDialog() {
        var showDialog by remember { mutableStateOf(true) }

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Text(
                            text = stringResource(R.string.TTT_init),
                            fontSize = 20.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(50.dp))
                        Row {
                            Spacer(modifier = Modifier.width(10.dp))
                            Box(
                                modifier = Modifier
                                    .border(1.dp, Color.Black)
                                    .clickable {
                                        playerOneSymb='X'
                                        playerTwoSymb='O'
                                        showDialog=false
                                    }
                                    .size(110.dp, 110.dp),
                                contentAlignment = Alignment.Center
                            )
                            {
                                Text(
                                    text = "X",
                                    modifier = Modifier.fillMaxSize(),
                                    fontSize = 100.sp,
                                    color = playerOneColour,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(modifier = Modifier.width(50.dp))
                            Box(
                                modifier = Modifier
                                    .border(1.dp, Color.Black)
                                    .clickable {
                                        playerOneSymb='O'
                                        playerTwoSymb='X'
                                        showDialog=false
                                    }
                                    .size(110.dp, 110.dp),
                                contentAlignment = Alignment.Center
                            )
                            {
                                Text(
                                    text = "O",
                                    modifier = Modifier.fillMaxSize(),
                                    fontSize = 100.sp,
                                    color = playerOneColour,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))

                        }
                        Spacer(modifier = Modifier.height(30.dp))
                    }

                }
            }
        }
    }
}