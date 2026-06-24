package com.example.androidapp.network.apis

import com.example.androidapp.network.dtos.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//data class Post(
//    val userId: Int = 0,
//    val id: Int? = null,
//    val title: String,
//    val body: String
//)

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<UserDto>

    @POST("users")
    suspend fun createUser(@Body post: UserDto): UserDto
}