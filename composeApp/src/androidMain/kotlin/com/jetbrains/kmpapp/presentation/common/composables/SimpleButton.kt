package com.jetbrains.kmpapp.presentation.common.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.presentation.theme.Gray
import com.jetbrains.kmpapp.presentation.theme.Orange
import com.jetbrains.kmpapp.presentation.theme.White

@Composable
fun RedButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnable: Boolean = true,
    isInvert: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.wrapContentHeight(),
        colors = ButtonColors(
            containerColor = if (isInvert) White else Orange,
            contentColor = if (isInvert) Orange else White,
            disabledContainerColor = Gray,
            disabledContentColor = White
        ),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            1.dp,
            color = if (isEnable) Orange else Gray
        ),
        enabled = isEnable,
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RedButtonPreview() {
    RedButton(text = "Button", isInvert = true, isEnable = false) {

    }
}