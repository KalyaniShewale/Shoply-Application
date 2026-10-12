package com.kalyani.shewale.shoplyapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalyani.shewale.shoplyapplication.R
import com.kalyani.shewale.shoplyapplication.ui.components.AppTextField
import com.kalyani.shewale.shoplyapplication.ui.components.CaptionText
import com.kalyani.shewale.shoplyapplication.ui.components.PrimaryButton
import com.kalyani.shewale.shoplyapplication.ui.components.ProductImage
import com.kalyani.shewale.shoplyapplication.ui.theme.ShoplyApplicationTheme

private const val MIN_PHOTOS = 3
private const val MAX_PHOTOS = 5
private val categories = listOf("Electronics", "Fashion", "Home", "Books", "Sports", "Other")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onUploadClick: () -> Unit = {}
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf<String?>(null) }
    var photoCount by rememberSaveable { mutableIntStateOf(0) }

    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.upload_title),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PhotoSection(
                photoCount = photoCount,
                onAddClick = { if (photoCount < MAX_PHOTOS) photoCount++ },
                onRemoveClick = { if (photoCount > 0) photoCount-- },
                enabled = !isLoading
            )

            AppTextField(
                value = title,
                onValueChange = { title = it },
                label = stringResource(R.string.upload_name_label),
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            CategorySelector(
                selected = category,
                onSelected = { category = it },
                enabled = !isLoading
            )

            AppTextField(
                value = description,
                onValueChange = { description = it },
                label = stringResource(R.string.upload_description_label),
                placeholder = stringResource(R.string.upload_description_hint),
                singleLine = false,
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp)
            )

            AppTextField(
                value = price,
                onValueChange = { price = it },
                label = stringResource(R.string.upload_price_label),
                leadingIcon = Icons.Outlined.AttachMoney,
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            PrimaryButton(
                text = stringResource(R.string.upload_button),
                onClick = {
                    focusManager.clearFocus()
                    onUploadClick()
                },
                isLoading = isLoading,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PhotoSection(
    photoCount: Int,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    enabled: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CaptionText(stringResource(R.string.upload_photos_label, MIN_PHOTOS))
            CaptionText(stringResource(R.string.upload_photos_count, photoCount, MAX_PHOTOS))
        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (photoCount < MAX_PHOTOS) {
                item {
                    OutlinedCard(
                        onClick = onAddClick,
                        enabled = enabled,
                        modifier = Modifier.size(80.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AddAPhoto,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            CaptionText(stringResource(R.string.upload_add_photo))
                        }
                    }
                }
            }
            items(count = photoCount) {
                Box(modifier = Modifier.size(80.dp)) {
                    ProductImage(
                        imageUrl = null,
                        modifier = Modifier.fillMaxSize(),
                        shape = MaterialTheme.shapes.medium
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.6f))
                            .clickable(enabled = enabled, onClick = onRemoveClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.upload_remove_photo),
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategorySelector(
    selected: String?,
    onSelected: (String) -> Unit,
    enabled: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        CaptionText(stringResource(R.string.upload_category_label))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categories) { name ->
                FilterChip(
                    selected = selected == name,
                    onClick = { onSelected(name) },
                    enabled = enabled,
                    label = { Text(name) }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun UploadScreenPreview() {
    ShoplyApplicationTheme { UploadScreen() }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun UploadScreenLoadingPreview() {
    ShoplyApplicationTheme { UploadScreen(isLoading = true) }
}