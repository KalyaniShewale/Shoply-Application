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
import com.kalyani.shewale.shoplyapplication.ui.screen.HomeScreen
import com.kalyani.shewale.shoplyapplication.ui.screen.LoginScreen
import com.kalyani.shewale.shoplyapplication.ui.screen.ProductDetailsScreen
import com.kalyani.shewale.shoplyapplication.ui.screen.RegisterScreen
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kalyani.shewale.shoplyapplication.auth.AuthEvent
import com.kalyani.shewale.shoplyapplication.ui.screen.FavoritesScreen
import com.kalyani.shewale.shoplyapplication.ui.screen.UploadScreen
import com.kalyani.shewale.shoplyapplication.viewmodel.LoginViewModel
import com.kalyani.shewale.shoplyapplication.viewmodel.RegisterViewModel
import com.kalyani.shewale.shoplyapplication.viewmodel.SessionViewModel
import com.kalyani.shewale.shoplyapplication.viewmodel.HomeViewModel
import com.kalyani.shewale.shoplyapplication.viewmodel.ProductDetailsViewModel

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    sessionViewModel: SessionViewModel = hiltViewModel(),
    startDestination: AppRoute = sessionViewModel.startDestination
) {
    val favoriteIds = remember { mutableStateListOf<String>() }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val showBottomBar = bottomNavItems.any { currentDestination.isRoute(it.route) }

    fun navigateToTab(route: AppRoute) {
        navController.navigate(route) {
            popUpTo<AppRoute.Home> { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun toggleFavorite(productId: String) {
        if (!favoriteIds.remove(productId)) favoriteIds.add(productId)
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                AppBottomBar(
                    currentDestination = currentDestination,
                    onItemClick = { route -> navigateToTab(route) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable<AppRoute.Login> {
                val viewModel: LoginViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                // Listens for the one-time "go to Home" event
                LaunchedEffect(Unit) {
                    viewModel.events.collect { event ->
                        when (event) {
                            AuthEvent.NavigateToHome -> navController.navigate(AppRoute.Home) {
                                popUpTo<AppRoute.Login> { inclusive = true }
                            }
                        }
                    }
                }

                LoginScreen(
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage?.let { stringResource(it) },
                    onLoginClick = { email, password -> viewModel.login(email, password) },
                    onRegisterClick = {
                        navController.navigate(AppRoute.Register) { launchSingleTop = true }
                    }
                )
            }

            composable<AppRoute.Register> {
                val viewModel: RegisterViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(Unit) {
                    viewModel.events.collect { event ->
                        when (event) {
                            AuthEvent.NavigateToHome -> navController.navigate(AppRoute.Home) {
                                popUpTo<AppRoute.Login> { inclusive = true }
                            }
                        }
                    }
                }

                RegisterScreen(
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage?.let { stringResource(it) },
                    onRegisterClick = { name, email, phone, password ->
                        viewModel.register(name, email, phone, password)
                    },
                    onBackClick = { navController.popBackStack() },
                    onLoginClick = { navController.popBackStack() }
                )
            }

            composable<AppRoute.Home> {
                val viewModel: HomeViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                HomeScreen(
                    uiState = uiState,
                    favoriteIds = favoriteIds,
                    onQueryChange = viewModel::onQueryChange,
                    onCategorySelected = viewModel::onCategorySelected,
                    onRetryClick = viewModel::loadProducts,
                    onProductClick = { productId ->
                        navController.navigate(AppRoute.ProductDetails(productId))
                    },
                    onFavoriteClick = { productId -> toggleFavorite(productId) },
                    onLogoutClick = {
                        sessionViewModel.logout()
                        navController.navigate(AppRoute.Login) {
                            popUpTo<AppRoute.Home> { inclusive = true }
                        }
                    }
                )
            }

            composable<AppRoute.Favorites> {
                FavoritesScreen(
                    products = emptyList(),
                    onProductClick = { productId ->
                        navController.navigate(AppRoute.ProductDetails(productId))
                    },
                    onRemoveFavoriteClick = { productId -> toggleFavorite(productId) },
                    onBrowseClick = { navigateToTab(AppRoute.Home) }
                )
            }

            composable<AppRoute.Upload> {
                UploadScreen(
                    onUploadClick = { navigateToTab(AppRoute.Home) }
                )
            }

            composable<AppRoute.ProductDetails> { backStackEntry ->
                val args = backStackEntry.toRoute<AppRoute.ProductDetails>()
                val viewModel: ProductDetailsViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val context = LocalContext.current

                ProductDetailsScreen(
                    product = uiState.product,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage?.let { stringResource(it) },
                    // TEMPORARY: favorites come from Room in the next step
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
}