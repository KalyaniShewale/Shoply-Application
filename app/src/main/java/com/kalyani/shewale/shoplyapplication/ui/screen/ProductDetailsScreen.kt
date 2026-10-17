package com.kalyani.shewale.shoplyapplication.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalyani.shewale.shoplyapplication.R
import com.kalyani.shewale.shoplyapplication.model.Product
import com.kalyani.shewale.shoplyapplication.ui.components.AppText
import com.kalyani.shewale.shoplyapplication.ui.components.BodyText
import com.kalyani.shewale.shoplyapplication.ui.components.CaptionText
import com.kalyani.shewale.shoplyapplication.ui.components.FavoriteButton
import com.kalyani.shewale.shoplyapplication.ui.components.ImageGallery
import com.kalyani.shewale.shoplyapplication.ui.components.PriceText
import com.kalyani.shewale.shoplyapplication.ui.components.PrimaryButton
import com.kalyani.shewale.shoplyapplication.ui.components.SellerCard
import com.kalyani.shewale.shoplyapplication.ui.components.TitleText
import com.kalyani.shewale.shoplyapplication.ui.theme.ShoplyApplicationTheme
import androidx.compose.material3.CircularProgressIndicator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    product: Product?,
    isFavorite: Boolean,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onCallClick: (phone: String) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.details_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    if (product != null) {
                        FavoriteButton(isFavorite = isFavorite, onClick = onFavoriteClick)
                    }
                }
            )
        },
        bottomBar = {
            if (product != null) {
                Box(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    PrimaryButton(
                        text = stringResource(
                            if (isFavorite) R.string.favorite_remove else R.string.favorite_add
                        ),
                        onClick = onFavoriteClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    ) { innerPadding ->
        if (product == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    AppText(
                        text = errorMessage ?: stringResource(R.string.details_not_found),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ImageGallery(imageUrls = product.imageUrls)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        TitleText(product.title)
                        CaptionText(product.category)
                    }
                    PriceText(product.price, style = MaterialTheme.typography.headlineSmall)
                }

                BodyText(product.description)

                // Recommended (FakeStore) products have no uploader, so this card is skipped
                product.seller?.let { seller ->
                    SellerCard(
                        seller = seller,
                        onCallClick = { onCallClick(seller.phone) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProductDetailsPreview() {
    ShoplyApplicationTheme {

    }
}