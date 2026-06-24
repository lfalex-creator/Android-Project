package com.example.androidapp.ui.screens

import android.R.attr.imeOptions
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidapp.R
import com.example.androidapp.util.isValidEmail
import com.example.androidapp.util.isValidPassword
import com.example.androidapp.viewModels.AuthViewModel
import com.example.androidapp.viewModels.BSViewModel
import kotlin.Boolean
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onRegisterClick: (email: String, password: String) -> Unit = { _, _ -> },
    isLoading: Boolean = false,
    errorMessage: String? = null,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var passwordVisibility by remember { mutableStateOf(false) }
    val emailErrorText=stringResource(R.string.emailerr)
    val passwordErrorText=stringResource(R.string.passerr)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = stringResource(R.string.welcome),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = stringResource(R.string.register_regtext),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { newValue ->
                email = newValue
                emailError = null
            },
            label = {
                Text(stringResource(R.string.email))
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = null
                )
            },
            isError = emailError?.let {
                true
            } ?: false,
            supportingText = emailError?.let { { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        Spacer(
            modifier = Modifier.height(16.dp)

        )
        OutlinedTextField(
            value = password,
            onValueChange = { newValue ->
                password = newValue
                passwordError = null
            },
            label = {
                Text(stringResource(R.string.password))
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Password,
                    contentDescription = null
                )
            },
            isError = passwordError?.let {
                true
            } ?: false,
            supportingText = passwordError?.let { { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done

            ),

            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },

            trailingIcon = {
                IconButton(
                    { passwordVisibility = !passwordVisibility }

                ) {
                    Icon(
                        if (passwordVisibility) {
                            Icons.Default.VisibilityOff
                        } else {
                            Icons.Default.Visibility
                        },

                        if (passwordVisibility) stringResource(R.string.showpass) else stringResource(R.string.hidepass),


                        )
                }
            }
        )
        Spacer(
            modifier = Modifier.height(16.dp)

        )
        Button(
            {
                var valid = true
                if (!email.isValidEmail()) {
                    emailError = emailErrorText
                    valid = false
                }
                if (!password.isValidPassword()) {
                    passwordError = passwordErrorText
                    valid = false
                }
                if (valid) {
                    emailError = null
                    passwordError = null
                    viewModel.insert(email)
                    onRegisterClick(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            when (isLoading) {
                true -> CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )

                false -> Text(stringResource(R.string.register))
            }
        }
        errorMessage?.let { error ->
            Spacer(
                modifier = Modifier.height(8.dp)

            )
            Text(error)
        }
        Spacer(
            modifier = Modifier.height(16.dp)

        )
        TextButton(
            onLoginClick
        ) {
            Text(stringResource(R.string.register_tologin))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}