package com.kalyani.shewale.shoplyapplication.model

data class Seller(
    val name: String,
    val phone: String,
    val email: String
)

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrls: List<String>,
    val seller: Seller? = null
)