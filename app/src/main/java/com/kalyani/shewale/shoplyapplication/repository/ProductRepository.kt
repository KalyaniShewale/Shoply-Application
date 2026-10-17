package com.kalyani.shewale.shoplyapplication.repository

import com.kalyani.shewale.shoplyapplication.model.Product

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
    suspend fun getProduct(id: String): Result<Product>
}