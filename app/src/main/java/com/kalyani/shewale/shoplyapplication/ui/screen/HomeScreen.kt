package com.kalyani.shewale.shoplyapplication.ui.screen

import com.kalyani.shewale.shoplyapplication.model.Product
import com.kalyani.shewale.shoplyapplication.model.SampleData
import com.kalyani.shewale.shoplyapplication.ui.components.CategoryChips
import com.kalyani.shewale.shoplyapplication.ui.components.ProductCard
import com.kalyani.shewale.shoplyapplication.ui.components.RecommendedCard
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    products: List<Product> = SampleData.products,
    recommended: List<Product> = SampleData.recommended,
    favoriteIds: List<String> = emptyList(),
    onProductClick: (productId: String) -> Unit = {},
    onFavoriteClick: (productId: String) -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    var query by rememberSaveable { mutableStateOf("") }
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }

    val categories = remember(products) { products.map { it.category }.distinct() }
    // to check No Product found condition
    //val products: List<Product> = emptyList()
    val filtered = remember(products, query, selectedCategory) {
        products.filter { product ->
            val matchesCategory = selectedCategory == null || product.category == selectedCategory
            val matchesQuery = query.isBlank() ||
                    product.title.contains(query.trim(), ignoreCase = true) ||
                    product.description.contains(query.trim(), ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }


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
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                CategoryChips(
                    categories = categories,
                    selected = selectedCategory,
                    onSelected = { selectedCategory = it }
                )
            }

            item { SectionHeader(stringResource(R.string.home_latest)) }

            if (filtered.isEmpty()) {
                item {
                    AppText(
                        text = stringResource(R.string.home_no_results),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(filtered, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        isFavorite = product.id in favoriteIds,
                        onClick = { onProductClick(product.id) },
                        onFavoriteClick = { onFavoriteClick(product.id) }
                    )
                }
            }

            if (recommended.isNotEmpty()) {
                item { SectionHeader(stringResource(R.string.home_recommended)) }
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(recommended, key = { it.id }) { product ->
                            RecommendedCard(
                                product = product,
                                onClick = { onProductClick(product.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ShoplyApplicationTheme { HomeScreen(favoriteIds = listOf("2")) }
}