package com.example.androidapp.ui.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.androidapp.ui.data.entities.GameEntity
@Dao
interface GameDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGame(GameEntity: GameEntity)
}