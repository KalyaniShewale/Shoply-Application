package com.kalyani.shewale.shoplyapplication.repository

import com.kalyani.shewale.shoplyapplication.model.Product
import com.kalyani.shewale.shoplyapplication.model.ProductNotFoundException
import com.kalyani.shewale.shoplyapplication.model.toProduct
import com.kalyani.shewale.shoplyapplication.networkcall.FakeStoreApi
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ProductRepositoryImpl @Inject constructor(
    private val api: FakeStoreApi
) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            Result.success(api.getProducts().map { it.toProduct() })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProduct(id: String): Result<Product> {
        val apiId = id.toIntOrNull()
            ?: return Result.failure(ProductNotFoundException())

        return try {
            val dto = api.getProduct(apiId)
            if (dto == null) Result.failure(ProductNotFoundException())
            else Result.success(dto.toProduct())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}