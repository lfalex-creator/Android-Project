package com.example.androidapp.ui.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)