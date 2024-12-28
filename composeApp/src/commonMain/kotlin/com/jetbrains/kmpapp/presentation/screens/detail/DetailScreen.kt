package com.jetbrains.kmpapp.presentation.screens.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.jetbrains.kmpapp.CONST.ACTION_EXIT
import com.jetbrains.kmpapp.CONST.ACTION_SEND
import com.jetbrains.kmpapp.data.MuseumObject
import com.jetbrains.kmpapp.presentation.AppViewModel
import com.jetbrains.kmpapp.presentation.screens.common.ActionButton
import com.jetbrains.kmpapp.presentation.screens.common.composables.EmptyScreenContent
import com.jetbrains.kmpapp.presentation.screens.common.ScreenState
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.label_artist
import kmp_app_template.composeapp.generated.resources.label_credits
import kmp_app_template.composeapp.generated.resources.label_date
import kmp_app_template.composeapp.generated.resources.label_department
import kmp_app_template.composeapp.generated.resources.label_dimensions
import kmp_app_template.composeapp.generated.resources.label_medium
import kmp_app_template.composeapp.generated.resources.label_repository
import kmp_app_template.composeapp.generated.resources.label_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


data class DetailScreen(val objectId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<DetailScreenModel>()
        val appViewModel = koinViewModel<AppViewModel>()

        val obj by viewModel.getObject(objectId).collectAsStateWithLifecycle(initialValue = null)

        var isEnabled by remember { mutableStateOf(true) }
        var error: String? by remember { mutableStateOf(null) }

        appViewModel.setScreenState(
            ScreenState(
                title = "",
                isBackArrowEnable = true,
                onBackPressed = { navigator.pop() },
                actionButtons = listOf(
                    ActionButton(
                        key = ACTION_EXIT,
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        isEnable = isEnabled,
                        onClick = { }
                    ),
                    ActionButton(
                        key = ACTION_SEND,
                        icon = Icons.AutoMirrored.Filled.Send,
                        onClick = { isEnabled = !isEnabled }
                    )
                ),
                onRefresh = {
                    viewModel.refresh(objectId)
                    error = "Ошибка сервера"
                },
                error = error,
                onErrorHandled = { error = null }
            )
        )

        AnimatedContent(obj != null) { objectAvailable ->
            if (objectAvailable) {
                obj?.let { ObjectDetails(it) }
            } else {
                EmptyScreenContent(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun ObjectDetails(
    obj: MuseumObject,
    modifier: Modifier = Modifier,
) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = obj.primaryImageSmall,
            contentDescription = obj.title,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
        )

        SelectionContainer {
            Column(Modifier.padding(12.dp)) {
                Text(obj.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(6.dp))
                LabeledInfo(stringResource(Res.string.label_title), obj.title)
                LabeledInfo(stringResource(Res.string.label_artist), obj.artistDisplayName)
                LabeledInfo(stringResource(Res.string.label_date), obj.objectDate)
                LabeledInfo(stringResource(Res.string.label_dimensions), obj.dimensions)
                LabeledInfo(stringResource(Res.string.label_medium), obj.medium)
                LabeledInfo(stringResource(Res.string.label_department), obj.department)
                LabeledInfo(stringResource(Res.string.label_repository), obj.repository)
                LabeledInfo(stringResource(Res.string.label_credits), obj.creditLine)
            }
        }
    }
}

@Composable
private fun LabeledInfo(
    label: String,
    data: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(vertical = 4.dp)) {
        Spacer(Modifier.height(6.dp))
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("$label: ")
                }
                append(data)
            }
        )
    }
}
