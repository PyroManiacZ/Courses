package ru.kechkinnd.features.auth.data

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = false
)