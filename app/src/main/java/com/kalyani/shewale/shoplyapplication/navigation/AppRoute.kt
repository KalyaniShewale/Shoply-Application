package com.kalyani.shewale.shoplyapplication.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute {

    @Serializable data object Favorites : AppRoute
    @Serializable data object Upload : AppRoute

    @Serializable data class ProductDetails(val productId: String) : AppRoute

    @Serializable
    data object Login : AppRoute
    @Serializable
    data object Register : AppRoute
    @Serializable
    data object Home : AppRoute
}