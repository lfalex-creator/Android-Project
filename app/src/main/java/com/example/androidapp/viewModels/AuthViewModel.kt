package com.example.androidapp.viewModels

import android.os.Message
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class AuthState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


class AuthViewModel : ViewModel() {
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

}