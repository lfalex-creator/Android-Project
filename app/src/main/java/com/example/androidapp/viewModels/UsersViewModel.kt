package com.example.androidapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapp.ui.data.AppDataBase
import com.example.androidapp.ui.data.entities.GamesDataEntity
import com.example.androidapp.ui.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.text.insert

class UsersViewModel(
    application: Application
) : AndroidViewModel(application) //ofera context la instantierea bazei de date
{
    private val userDao = AppDataBase.getDatabase(application).userDao()
    val users = userDao.getAll().stateIn(
        scope = viewModelScope,//pt corutina in backgr
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),//porneste la collect
        initialValue = emptyList()
    )
    fun insert(email: String)
    {
        viewModelScope.launch{
            userDao.insert(UserEntity(email = email))
        }
    }
    fun getUserGames(id: Long) : Flow<List<GamesDataEntity>>
    {
        return userDao.getGamesOfUser(id).stateIn(
            scope = viewModelScope,//pt corutina in backgr
            started = SharingStarted.WhileSubscribed(),//porneste la collect
            initialValue = emptyList()
        )
    }

}