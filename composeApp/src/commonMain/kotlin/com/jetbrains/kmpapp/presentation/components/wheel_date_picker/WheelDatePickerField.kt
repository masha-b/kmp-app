package com.jetbrains.kmpapp.presentation.components.wheel_date_picker

import androidx.compose.foundation.clickable
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDate

@Composable
fun WheelDatePickerField(
    selectedDate: LocalDate?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    OutlinedTextField(
        value = selectedDate?.toString() ?: "Выберите дату",
        onValueChange = {},
        enabled = false,
        modifier = modifier.clickable {
            onClick.invoke()
        }
    )
}