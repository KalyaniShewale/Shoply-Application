package com.kalyani.shewale.shoplyapplication.networkcall

import com.kalyani.shewale.shoplyapplication.model.FakeStoreProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {

    @GET("products")
    suspend fun getProducts(): List<FakeStoreProductDto>

    // FakeStore sends an empty body for an unknown id, so the result can be null
    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): FakeStoreProductDto?
}