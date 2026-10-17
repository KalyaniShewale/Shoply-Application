package com.kalyani.shewale.shoplyapplication.viewmodel


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kalyani.shewale.shoplyapplication.R
import com.kalyani.shewale.shoplyapplication.model.ProductNotFoundException
import com.kalyani.shewale.shoplyapplication.navigation.AppRoute
import com.kalyani.shewale.shoplyapplication.usecase.GetProductUseCase
import com.kalyani.shewale.shoplyapplication.usecase.ProductDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {

    private val productId: String =
        savedStateHandle.toRoute<AppRoute.ProductDetails>().productId

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = ProductDetailsUiState(isLoading = true)

            getProductUseCase(productId)
                .onSuccess { product ->
                    _uiState.value = ProductDetailsUiState(isLoading = false, product = product)
                }
                .onFailure { error ->
                    _uiState.value = ProductDetailsUiState(
                        isLoading = false,
                        // "Not found" uses the screen's own message, anything else is a load error
                        errorMessage = if (error is ProductNotFoundException) null
                        else R.string.details_load_error
                    )
                }
        }
    }
}