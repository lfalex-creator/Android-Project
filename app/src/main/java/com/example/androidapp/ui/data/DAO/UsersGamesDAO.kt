package com.example.androidapp.ui.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.androidapp.ui.data.entities.UserGameEntity
@Dao
interface UsersGamesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGame(userGameEntity: UserGameEntity)
}