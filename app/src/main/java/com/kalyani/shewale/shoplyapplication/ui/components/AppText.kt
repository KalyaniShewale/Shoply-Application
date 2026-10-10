package com.kalyani.shewale.shoplyapplication.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.kalyani.shewale.shoplyapplication.R

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        style = style
    )
}

@Composable
fun TitleText(text: String, modifier: Modifier = Modifier) {
    AppText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun SectionTitleText(text: String, modifier: Modifier = Modifier) {
    AppText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun ItemTitleText(text: String, modifier: Modifier = Modifier) {
    AppText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun BodyText(text: String, modifier: Modifier = Modifier) {
    AppText(text = text, modifier = modifier)
}

@Composable
fun CaptionText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE
) {
    AppText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun PriceText(
    price: Double,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleSmall
) {
    AppText(
        text = stringResource(R.string.price_format, price),
        modifier = modifier,
        style = style,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Medium
    )
}