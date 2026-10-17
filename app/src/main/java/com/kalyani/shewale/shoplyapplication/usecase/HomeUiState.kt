package com.kalyani.shewale.shoplyapplication.usecase

import androidx.annotation.StringRes
import com.kalyani.shewale.shoplyapplication.model.Product

data class HomeUiState(
    val isLoading: Boolean = true,
    val products: List<Product> = emptyList(),
    @StringRes val errorMessage: Int? = null,
    val query: String = "",
    val selectedCategory: String? = null
) {
    val categories: List<String>
        get() = products.map { it.category }.distinct()

    // Search and category filter applied to the loaded list
    val filteredProducts: List<Product>
        get() {
            val text = query.trim()
            return products.filter { product ->
                val matchesCategory = selectedCategory == null || product.category == selectedCategory
                val matchesQuery = text.isBlank() ||
                        product.title.contains(text, ignoreCase = true) ||
                        product.description.contains(text, ignoreCase = true)
                matchesCategory && matchesQuery
            }
        }
}