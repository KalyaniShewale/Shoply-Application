package com.kalyani.shewale.shoplyapplication.ui.screen

import com.kalyani.shewale.shoplyapplication.model.Product
import com.kalyani.shewale.shoplyapplication.ui.components.CategoryChips
import com.kalyani.shewale.shoplyapplication.ui.components.ProductCard
import com.kalyani.shewale.shoplyapplication.ui.components.SearchField
import com.kalyani.shewale.shoplyapplication.ui.components.SectionHeader
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalyani.shewale.shoplyapplication.R
import com.kalyani.shewale.shoplyapplication.ui.components.AppText
import com.kalyani.shewale.shoplyapplication.ui.theme.ShoplyApplicationTheme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import com.kalyani.shewale.shoplyapplication.model.Seller
import com.kalyani.shewale.shoplyapplication.usecase.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState = HomeUiState(),
    favoriteIds: List<String> = emptyList(),
    onQueryChange: (String) -> Unit = {},
    onCategorySelected: (String?) -> Unit = {},
    onRetryClick: () -> Unit = {},
    onProductClick: (productId: String) -> Unit = {},
    onFavoriteClick: (productId: String) -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val filtered = uiState.filteredProducts

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home_title),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = stringResource(R.string.home_logout)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                SearchField(
                    value = uiState.query,
                    onValueChange = onQueryChange,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                CategoryChips(
                    categories = uiState.categories,
                    selected = uiState.selectedCategory,
                    onSelected = onCategorySelected
                )
            }

            item { SectionHeader(stringResource(R.string.home_latest)) }

            when {
                uiState.isLoading -> item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.errorMessage != null -> item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AppText(
                            text = stringResource(uiState.errorMessage),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        TextButton(onClick = onRetryClick) {
                            Text(stringResource(R.string.recommended_retry))
                        }
                    }
                }

                filtered.isEmpty() -> item {
                    AppText(
                        text = stringResource(R.string.home_no_results),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                else -> items(filtered, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        isFavorite = product.id in favoriteIds,
                        onClick = { onProductClick(product.id) },
                        onFavoriteClick = { onFavoriteClick(product.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ShoplyApplicationTheme {
        HomeScreen(
            uiState = HomeUiState(isLoading = false, products = PreviewData.products),
            favoriteIds = listOf("2")
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenLoadingPreview() {
    ShoplyApplicationTheme { HomeScreen(uiState = HomeUiState(isLoading = true)) }
}


object PreviewData {
    val products = listOf(
        Product("1", "Fjallraven Backpack", "Your perfect pack for everyday use and walks in the forest.", 109.95, "Men's clothing", listOf(""),
            Seller("Alex Morgan", "+447700900123", "alex@email.com")
        ),
        Product("2", "Slim Fit T-Shirt", "Slim fitting style, contrast raglan long sleeve, three button henley placket.", 22.3, "Men's clothing", listOf("")),
        Product("3", "Gold Bracelet", "From our Legends Collection, inspired by the mythical water dragon.", 695.0, "Jewelery", listOf(""))
    )
}