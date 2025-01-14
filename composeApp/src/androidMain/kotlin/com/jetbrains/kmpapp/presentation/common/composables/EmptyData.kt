package com.jetbrains.kmpapp.presentation.common.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.jetbrains.kmpapp.presentation.theme.Gray

@Composable
fun EmptyData(modifier: Modifier = Modifier, text: String = "Ничего не найдено") {
    Text(
        modifier = modifier.fillMaxSize(),
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Companion.Center,
        color = Gray
    )
}