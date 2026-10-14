package com.kalyani.shewale.shoplyapplication.auth

import androidx.annotation.StringRes

data class AuthUiState(
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
)

sealed interface AuthEvent {
    data object NavigateToHome : AuthEvent
}