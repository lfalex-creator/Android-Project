package com.example.androidapp.ui.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,//=0 este ignorat, pt ca autoGenerate pune singur valoarea corecta
    val email: String
)
