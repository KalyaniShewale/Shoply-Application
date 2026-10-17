package com.kalyani.shewale.shoplyapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalyani.shewale.shoplyapplication.R
import com.kalyani.shewale.shoplyapplication.usecase.GetProductsUseCase
import com.kalyani.shewale.shoplyapplication.usecase.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            getProductsUseCase()
                .onSuccess { products ->
                    _uiState.update { it.copy(isLoading = false, products = products) }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = R.string.recommended_load_error)
                    }
                }
        }
    }

    fun onQueryChange(query: String) = _uiState.update { it.copy(query = query) }

    fun onCategorySelected(category: String?) =
        _uiState.update { it.copy(selectedCategory = category) }
}