package com.example.androidapp.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapp.network.dtos.UserDto
import com.example.androidapp.network.RetrofitClient
import com.example.androidapp.network.dtos.toEntity
import com.example.androidapp.ui.data.AppDataBase
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class NetworkState(
    val isLoading: Boolean = false,
    val users: List<UserDto> = emptyList(),
    val error: String? = null
)

class NetworkViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(NetworkState())
    val state: StateFlow<NetworkState> = _state

    private val prefs = application.getSharedPreferences("leaderboard_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val userDao = AppDataBase.getDatabase(application).userDao()

    fun loadPosts() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val savedJson = prefs.getString("leaderboard", null)

                if(savedJson != null) {
                    val type = object : TypeToken<List<UserDto>>() {}.type
                    val savedUsers: List<UserDto> = gson.fromJson(savedJson, type)
                    _state.value = _state.value.copy(isLoading = false, users = savedUsers)
                } else {

                    val fetchedUsers = RetrofitClient.api.getUsers()

                    val usersWithScores = fetchedUsers.map { user ->
                        user.copy(score = (10..100).random().toLong())
                    }.sortedByDescending { it.score }

                    usersWithScores.forEach { userDto ->
                        userDao.insert(userDto.toEntity())
                    }

                    saveToPrefs(usersWithScores)
                    _state.value = _state.value.copy(isLoading = false, users = usersWithScores)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun sendUser(email: String, score: Long, id: Long = 1L, onResult: (UserDto?) -> Unit = {}) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val newUser = RetrofitClient.api.createUser(UserDto(id = id, email = email, score = score))
                val currentList = _state.value.users.toMutableList()
                val existingIndex = currentList.indexOfFirst { it.email == newUser.email }

                if(existingIndex != -1){
                    currentList[existingIndex] = newUser
                } else {
                    currentList.add(newUser)
                }

                currentList.sortByDescending { it.score }

                saveToPrefs(currentList)
                _state.value = _state.value.copy(isLoading = false, users = currentList)
                onResult(newUser)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
                onResult(null)
            }
        }
    }

    private fun saveToPrefs(users: List<UserDto>) {
        val json = gson.toJson(users)
        prefs.edit().putString("leaderboard", json).apply()
    }
}