package com.kalyani.shewale.shoplyapplication.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalyani.shewale.shoplyapplication.auth.AuthEvent
import com.kalyani.shewale.shoplyapplication.auth.AuthUiState
import com.kalyani.shewale.shoplyapplication.auth.toAuthError
import com.kalyani.shewale.shoplyapplication.auth.toMessageRes
import com.kalyani.shewale.shoplyapplication.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _events = Channel<AuthEvent>(Channel.BUFFERED)
    val events: Flow<AuthEvent> = _events.receiveAsFlow()

    fun login(email: String, password: String) {
        if (_uiState.value.isLoading) return   // ignore double taps

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = loginUseCase(email, password)

            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false) }
                _events.send(AuthEvent.NavigateToHome)
            } else {
                val error = result.exceptionOrNull().toAuthError()
                _uiState.update { it.copy(isLoading = false, errorMessage = error.toMessageRes()) }
            }
        }
    }
}