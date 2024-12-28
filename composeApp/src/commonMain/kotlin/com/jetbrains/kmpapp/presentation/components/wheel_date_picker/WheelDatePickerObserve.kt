package com.jetbrains.kmpapp.presentation.components.wheel_date_picker

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate

@Composable
fun WheelDatePickerObserve(showPicker: Boolean, onSelectDate: (LocalDate?) -> Unit, onDismiss: () -> Unit) {
    if (showPicker) {
        WheelDatePickerBottomSheet(showPicker,
            onSelectDate = { date ->
                onSelectDate(date)
            }, onDismiss = {
                onDismiss()
            })
    }
}