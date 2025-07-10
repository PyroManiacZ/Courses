package ru.kechkinnd.features.auth.data

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.update {
            val isEmailValid = isValidEmail(email)
            val isLoginEnabled = isEmailValid && it.password.isNotBlank()

            it.copy(
                email = email,
                isEmailValid = isEmailValid,
                isLoginEnabled = isLoginEnabled
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            val isLoginEnabled = it.isEmailValid && password.isNotBlank()
            it.copy(
                password = password,
                isLoginEnabled = isLoginEnabled
            )
        }
    }

    fun onLoginClick() {
        // TODO: навигация
    }

    private fun isValidEmail(email: String): Boolean {
        return email.all { it.code < 128 } &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
