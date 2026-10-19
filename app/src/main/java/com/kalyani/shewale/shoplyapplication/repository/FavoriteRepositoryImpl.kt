package com.kalyani.shewale.shoplyapplication.repository

import com.kalyani.shewale.shoplyapplication.model.Product
import com.kalyani.shewale.shoplyapplication.roomdatabase.FavoriteDao
import com.kalyani.shewale.shoplyapplication.roomdatabase.toEntity
import com.kalyani.shewale.shoplyapplication.roomdatabase.toProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao
) : FavoriteRepository {

    override fun observeFavorites(): Flow<List<Product>> =
        dao.observeAll().map { list -> list.map { it.toProduct() } }

    override suspend fun toggleFavorite(product: Product) {
        if (dao.exists(product.id)) dao.deleteById(product.id)
        else dao.insert(product.toEntity())
    }
}