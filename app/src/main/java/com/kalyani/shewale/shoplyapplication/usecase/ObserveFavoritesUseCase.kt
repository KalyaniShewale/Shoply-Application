package com.kalyani.shewale.shoplyapplication.usecase

import com.kalyani.shewale.shoplyapplication.model.Product
import com.kalyani.shewale.shoplyapplication.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<Product>> = repository.observeFavorites()
}