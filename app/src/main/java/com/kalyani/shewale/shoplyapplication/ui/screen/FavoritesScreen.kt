package com.kalyani.shewale.shoplyapplication.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalyani.shewale.shoplyapplication.R
import com.kalyani.shewale.shoplyapplication.model.Product
import com.kalyani.shewale.shoplyapplication.ui.components.EmptyState
import com.kalyani.shewale.shoplyapplication.ui.components.InfoBanner
import com.kalyani.shewale.shoplyapplication.ui.components.ProductCard
import com.kalyani.shewale.shoplyapplication.ui.theme.ShoplyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onProductClick: (productId: String) -> Unit = {},
    onRemoveFavoriteClick: (product: Product) -> Unit = {},
    onBrowseClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.favorites_title),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    ) { innerPadding ->
        if (products.isEmpty()) {
            EmptyState(
                icon = Icons.Outlined.FavoriteBorder,
                title = stringResource(R.string.favorites_empty_title),
                message = stringResource(R.string.favorites_empty_message),
                actionText = stringResource(R.string.favorites_browse),
                onActionClick = onBrowseClick,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    InfoBanner(
                        text = stringResource(R.string.favorites_saved_banner),
                        icon = Icons.Outlined.PhoneAndroid
                    )
                }
                items(products, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        isFavorite = true,
                        onClick = { onProductClick(product.id) },
                        onFavoriteClick = { onRemoveFavoriteClick(product) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FavoritesScreenPreview() {
    ShoplyApplicationTheme {  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FavoritesScreenEmptyPreview() {
    ShoplyApplicationTheme { FavoritesScreen(products = emptyList()) }
}