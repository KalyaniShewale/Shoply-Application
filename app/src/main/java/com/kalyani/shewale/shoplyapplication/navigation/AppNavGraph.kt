package com.kalyani.shewale.shoplyapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kalyani.shewale.shoplyapplication.ui.screen.HomeScreen
import com.kalyani.shewale.shoplyapplication.ui.screen.LoginScreen
import com.kalyani.shewale.shoplyapplication.ui.screen.RegisterScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: AppRoute = AppRoute.Login
) {
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
                onLogoutClick = {
                    navController.navigate(AppRoute.Login) {
                        popUpTo<AppRoute.Home> { inclusive = true }
                    }
                }
            )
        }
    }
}