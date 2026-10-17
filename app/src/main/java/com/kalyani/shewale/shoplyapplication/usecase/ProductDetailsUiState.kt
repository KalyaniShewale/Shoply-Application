package com.kalyani.shewale.shoplyapplication.usecase


import androidx.annotation.StringRes
import com.kalyani.shewale.shoplyapplication.model.Product

data class ProductDetailsUiState(
    val isLoading: Boolean = true,
    val product: Product? = null,
    @StringRes val errorMessage: Int? = null
)