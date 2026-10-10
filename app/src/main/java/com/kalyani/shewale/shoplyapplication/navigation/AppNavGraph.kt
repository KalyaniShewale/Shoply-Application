package com.kalyani.shewale.shoplyapplication.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kalyani.shewale.shoplyapplication.model.SampleData
import com.kalyani.shewale.shoplyapplication.ui.screen.HomeScreen
import com.kalyani.shewale.shoplyapplication.ui.screen.LoginScreen
import com.kalyani.shewale.shoplyapplication.ui.screen.ProductDetailsScreen
import com.kalyani.shewale.shoplyapplication.ui.screen.RegisterScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: AppRoute = AppRoute.Home
) {
    val favoriteIds = remember { mutableStateListOf<String>() }
    fun toggleFavorite(productId: String) {
        if (!favoriteIds.remove(productId)) favoriteIds.add(productId)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable<AppRoute.Login> {
            LoginScreen(
                // Firebase login need to replaces this
                onLoginClick = { _, _ ->
                    navController.navigate(AppRoute.Home) {
                        popUpTo<AppRoute.Login> { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(AppRoute.Register) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<AppRoute.Register> {
            RegisterScreen(
                // Firebase register need to replaces this
                onRegisterClick = { _, _, _, _ ->
                    navController.navigate(AppRoute.Home) {
                        popUpTo<AppRoute.Login> { inclusive = true }
                    }
                },
                onBackClick = { navController.popBackStack() },
                onLoginClick = { navController.popBackStack() }
            )
        }

        composable<AppRoute.Home> {
            HomeScreen(
                favoriteIds = favoriteIds,
                onProductClick = { productId ->
                    navController.navigate(AppRoute.ProductDetails(productId))
                },
                onFavoriteClick = { productId -> toggleFavorite(productId) },
                onLogoutClick = {
                    navController.navigate(AppRoute.Login) {
                        popUpTo<AppRoute.Home> { inclusive = true }
                    }
                }
            )
        }

        composable<AppRoute.ProductDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoute.ProductDetails>()
            val context = LocalContext.current

            ProductDetailsScreen(
                product = SampleData.findById(args.productId),
                isFavorite = args.productId in favoriteIds,
                onBackClick = { navController.popBackStack() },
                onFavoriteClick = { toggleFavorite(args.productId) },
                onCallClick = { phone ->
                    context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
                }
            )
        }
    }
}