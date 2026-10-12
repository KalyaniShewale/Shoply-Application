package com.kalyani.shewale.shoplyapplication.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.kalyani.shewale.shoplyapplication.R

data class BottomNavItem(
    val route: AppRoute,
    @StringRes val labelRes: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(AppRoute.Home, R.string.nav_home, Icons.Outlined.Home, Icons.Filled.Home),
    BottomNavItem(AppRoute.Favorites, R.string.nav_favorites, Icons.Outlined.FavoriteBorder, Icons.Filled.Favorite),
    BottomNavItem(AppRoute.Upload, R.string.nav_upload, Icons.Outlined.AddCircleOutline, Icons.Filled.AddCircle)
)

fun NavDestination?.isRoute(route: AppRoute): Boolean =
    this?.hierarchy?.any { it.hasRoute(route::class) } == true