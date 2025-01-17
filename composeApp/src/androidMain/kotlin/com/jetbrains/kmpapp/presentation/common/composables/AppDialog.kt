package com.jetbrains.kmpapp.presentation.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jetbrains.kmpapp.presentation.theme.Black
import com.jetbrains.kmpapp.presentation.theme.DarkGray
import com.jetbrains.kmpapp.presentation.theme.White
import kotlin.let
import kotlin.text.isNotBlank
import kotlin.text.orEmpty

@Composable
fun AppDialog(
    title: String,
    desc: String? = null,
    confirmButtonText: String,
    dismissButtonText: String = "Отмена",
    fieldInitText: String? = null,
    onFieldTextChanged: ((String) -> Unit)? = null,
    onConfirmRequest: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.background(White)
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.Companion.Start
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Black
                )

                desc?.let {
                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = DarkGray
                    )
                }

                onFieldTextChanged?.let {
                    SimpleTextField(
                        modifier = Modifier.padding(top = 24.dp),
                        value = fieldInitText.orEmpty(),
                        labelText = "Логин",
                        onValueChanged = it
                    )
                }

                Row(modifier = Modifier.padding(top = 24.dp)) {
                    RedButton(
                        modifier = Modifier.weight(1f),
                        text = dismissButtonText,
                        isInvert = true,
                        onClick = onDismissRequest
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    RedButton(
                        modifier = Modifier.weight(1f),
                        isEnable = onFieldTextChanged == null || fieldInitText?.isNotBlank() == true,
                        text = confirmButtonText,
                        onClick = onConfirmRequest
                    )
                }
            }
        }
    }
}