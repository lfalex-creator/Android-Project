package com.example.androidapp.network.dtos

import com.example.androidapp.ui.data.entities.UserEntity


data class UserDto(
    val id: Long,
    val email: String,
    val score: Long
)

fun UserDto.toEntity() = UserEntity(
    id = this.id,
    email = this.email
)

data class UserResponseDto(
    val id: Long,
    val email: String,
    val score: Long
)