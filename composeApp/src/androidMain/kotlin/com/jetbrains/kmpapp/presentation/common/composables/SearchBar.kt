package com.jetbrains.kmpapp.presentation.common.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.presentation.theme.Orange
import com.jetbrains.kmpapp.R
import com.jetbrains.kmpapp.presentation.theme.Gray
import com.jetbrains.kmpapp.presentation.theme.Transparent
import kotlin.text.isNotEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit = { },
    isEnable: Boolean = true,
    singleLine: Boolean = true,
    placeholder: String = "Найти..."
) {
    var text by remember { mutableStateOf(value) }
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChanged.invoke(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .height(TextFieldDefaults.MinHeight)
            .padding(bottom = 8.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        visualTransformation = VisualTransformation.Companion.None,
        interactionSource = interactionSource,
        singleLine = singleLine,
        enabled = isEnable,
        cursorBrush = SolidColor(Orange)
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = text,
            visualTransformation = VisualTransformation.Companion.None,
            innerTextField = innerTextField,
            singleLine = singleLine,
            enabled = isEnable,
            interactionSource = interactionSource,
            trailingIcon = {
                if (text.isNotEmpty())
                    ClearTextBtn {
                        text = ""
                        onValueChanged.invoke(text)
                    }
            },
            placeholder = { Text(text = placeholder, style = MaterialTheme.typography.bodyMedium) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Transparent,
                unfocusedContainerColor = Transparent,
                errorContainerColor = Transparent,
                focusedIndicatorColor = Orange,
                unfocusedIndicatorColor = Gray,
                unfocusedPlaceholderColor = Gray,
                focusedPlaceholderColor = Gray
            ),
            contentPadding = PaddingValues(8.dp, 8.dp)
        )
    }
}

@Composable
fun ClearTextBtn(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painterResource(R.drawable.ic_clear),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchBar() {
    SearchBar(value = "")
}