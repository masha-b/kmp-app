package com.jetbrains.kmpapp.presentation.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.kmpapp.extensions.clock.now
import com.jetbrains.kmpapp.presentation.components.wheel_date_picker.WheelDatePickerField
import com.jetbrains.kmpapp.presentation.components.wheel_date_picker.WheelDatePickerObserve
import com.jetbrains.kmpapp.presentation.screens.common.toolbar.ToolbarState
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

class AuthScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var showDatePicker by remember { mutableStateOf(false) }

        // Состояние для полей ввода
        var name by remember { mutableStateOf("") }
        var surname by remember { mutableStateOf("") }
        var birthDate by remember { mutableStateOf<LocalDate?>(null) }
        var gender by remember { mutableStateOf("Мужской") }
        var pushNotifications by remember { mutableStateOf(false) }

//        setToolbar.invoke(
//            ToolbarState(
//                title = "Авторизация",
//                isBackArrowEnable = true,
//                onBackPressed = { navigator.pop() }
//            )
//        )

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Поле Имя
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Имя") },
                isError = name.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )

            // Поле Фамилия
            OutlinedTextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text("Фамилия") },
                isError = surname.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )


            // Выбор даты рождения
            WheelDatePickerField(
                selectedDate = birthDate,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showDatePicker = !showDatePicker
                }
            )

            Text("Пол")
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GenderOption("Мужской", gender) { gender = it }
                GenderOption("Женский", gender) { gender = it }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Получать пуш-уведомления")
                Switch(
                    checked = pushNotifications,
                    onCheckedChange = { pushNotifications = it }
                )
            }

            for (i in 0 until 10) {
                Box(
                    modifier = Modifier
                        .padding(16.dp),
                ) {
                    Text("-----")
                }
            }
        }

        //AnimatedContent(true) { }

        WheelDatePickerObserve(
            showDatePicker,
            onSelectDate = { date ->
                if (Clock.Companion.now().year >= (date?.year ?: 0) + 18) {
                    birthDate = date
                }
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}

@Composable
private fun GenderOption(option: String, selected: String, onSelect: (String) -> Unit) {
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected == option,
            onClick = { onSelect(option) }
        )
        Text(option)
    }
}