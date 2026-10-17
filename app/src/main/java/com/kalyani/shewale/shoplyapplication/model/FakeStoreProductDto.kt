package com.kalyani.shewale.shoplyapplication.model

data class FakeStoreProductDto(
    val id: Int = 0,
    val title: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val category: String = "",
    val image: String = ""
)

fun FakeStoreProductDto.toProduct(): Product = Product(
    id = id.toString(),
    title = title,
    description = description,
    price = price,
    category = category.replaceFirstChar { it.uppercase() },
    imageUrls = listOf(image),
    seller = null   // API products have no uploader
)