package com.kalyani.shewale.shoplyapplication.roomdatabase


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kalyani.shewale.shoplyapplication.model.Product
import com.kalyani.shewale.shoplyapplication.model.Seller

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrls: String,
    val sellerName: String?,        // null when the product has no seller
    val sellerPhone: String?,
    val sellerEmail: String?,
    val addedAt: Long
)

fun Product.toEntity() = FavoriteEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    category = category,
    imageUrls = imageUrls.joinToString("\n"),
    sellerName = seller?.name,
    sellerPhone = seller?.phone,
    sellerEmail = seller?.email,
    addedAt = System.currentTimeMillis()
)

fun FavoriteEntity.toProduct() = Product(
    id = id,
    title = title,
    description = description,
    price = price,
    category = category,
    imageUrls = if (imageUrls.isBlank()) emptyList() else imageUrls.split("\n"),
    seller = sellerName?.let { Seller(it, sellerPhone.orEmpty(), sellerEmail.orEmpty()) }
)