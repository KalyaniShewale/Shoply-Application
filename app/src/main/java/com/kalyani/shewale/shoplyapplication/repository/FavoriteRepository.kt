package com.kalyani.shewale.shoplyapplication.repository

import com.kalyani.shewale.shoplyapplication.model.Product
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeFavorites(): Flow<List<Product>>
    suspend fun toggleFavorite(product: Product)
}