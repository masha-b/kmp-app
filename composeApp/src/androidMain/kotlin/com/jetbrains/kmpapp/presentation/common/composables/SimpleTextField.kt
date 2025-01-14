package com.jetbrains.kmpapp.presentation.common.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.presentation.theme.Gray
import com.jetbrains.kmpapp.presentation.theme.Orange
import com.jetbrains.kmpapp.presentation.theme.Red
import com.jetbrains.kmpapp.presentation.theme.Transparent
import kotlin.text.isNullOrBlank
import kotlin.text.orEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit = { },
    error: String? = null,
    isEnable: Boolean = true,
    singleLine: Boolean = true,
    labelText: String
) {
    var text by remember { mutableStateOf(value) }
    val interactionSource = remember { MutableInteractionSource() }
    val isError: Boolean = remember(key1 = error) { !error.isNullOrBlank() }

    Column {

        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onValueChanged.invoke(it)
            },
            modifier = modifier
                .size(TextFieldDefaults.MinWidth, TextFieldDefaults.MinHeight),
            textStyle = MaterialTheme.typography.bodySmall,
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
                isError = isError,
                interactionSource = interactionSource,
                label = { Text(text = labelText) },
                trailingIcon = {
                    if (isError) Icon(
                        Icons.Filled.Info,
                        error,
                        tint = Red
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Transparent,
                    unfocusedContainerColor = Transparent,
                    errorContainerColor = Transparent,
                    focusedIndicatorColor = Orange,
                    unfocusedIndicatorColor = Gray,
                    unfocusedLabelColor = Gray,
                    focusedLabelColor = Orange,
                    errorIndicatorColor = Red,
                    errorLabelColor = Red,
                    errorSupportingTextColor = Red
                ),
                contentPadding = PaddingValues(0.dp, 8.dp)
            )
        }

        if (isError) ErrorLabel(text = error.orEmpty())
    }
}

@Composable
fun ErrorLabel(text: String) {
    Text(
        modifier = Modifier.padding(0.dp, 4.dp),
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = Red
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SimpleTextField(value = "", labelText = "Логин", error = "error")
}