package com.example.androidapp.viewModels

import android.app.Application
import android.os.Message
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapp.ui.data.AppDataBase
import com.example.androidapp.ui.data.entities.UserEntity
import com.example.androidapp.util.PrefsHelper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


data class AuthState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


class AuthViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState
    private val _isLoggedIn = MutableStateFlow(auth.currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()



    init {
        auth.addAuthStateListener { firebaseAuth ->
            _isLoggedIn.value = firebaseAuth.currentUser != null
        }
    }
    fun login(email: String, password: String) {
        _authState.value= AuthState(true)

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.e("Login", "Success")
                PrefsHelper.saveLastEmail(getApplication(), email)
                insert(email)
                _authState.value= AuthState()
                //onSuccess()
            }
            .addOnFailureListener {error ->
                Log.e("Login", "Failed")
                _authState.value= AuthState(errorMessage = error.message )

            }
    }

    fun register(email: String, password: String) {
        _authState.value= AuthState(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.e("Register", "Success")
                PrefsHelper.saveLastEmail(getApplication(), email)

                _authState.value= AuthState()
                //onSuccess()
            }
            .addOnFailureListener {
                Log.e("Register", "Failed")
                _authState.value= AuthState(errorMessage = it.message )
            }
    }
    fun logout() {
        auth.signOut()
    }
    private val userDao = AppDataBase.getDatabase(application).userDao()
    fun insert(email: String)
    {
        viewModelScope.launch{
            val existingUsers = userDao.getAll().first()

            val emailExists = existingUsers.any { it.email == email }
            if(!emailExists) {
                userDao.insert(UserEntity(email = email))
            }
        }
    }
}