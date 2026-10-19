package com.kalyani.shewale.shoplyapplication.usecase

import com.kalyani.shewale.shoplyapplication.model.Product
import com.kalyani.shewale.shoplyapplication.repository.FavoriteRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(product: Product) = repository.toggleFavorite(product)
}