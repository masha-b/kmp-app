package com.jetbrains.kmpapp.presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.kmpapp.presentation.AppViewModel
import com.jetbrains.kmpapp.presentation.common.ScreenState
import com.jetbrains.kmpapp.presentation.common.composables.RedButton
import com.jetbrains.kmpapp.presentation.utils.debugInputPointer
import com.jetbrains.kmpapp.presentation.utils.rememberFlowWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.jetbrains.kmpapp.R
import com.jetbrains.kmpapp.domain.models.apps.VkpAppType
import com.jetbrains.kmpapp.presentation.common.composables.ErrorLabel
import com.jetbrains.kmpapp.presentation.screens.apps.AppsScreen
import com.jetbrains.kmpapp.utils.isEmailValid

class AuthScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<AuthViewModel>()
        val appViewModel = koinViewModel<AppViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = rememberFlowWithLifecycle(viewModel.effect)
        val context = LocalContext.current

        appViewModel.setScreenState(
            ScreenState(isEnable = false, isLoading = state.isLoading, isBottomNavigationEnable = false)
        )

        LaunchedEffect(effect) {
            effect.collect { action ->
                when (action) {
                    AuthReducer.AuthEffect.NavigateToCallLog -> navigator.replace(AppsScreen(VkpAppType.ANDROID))
                    is AuthReducer.AuthEffect.Error -> appViewModel.setScreenState(appViewModel.screenState.value.copy(error = action.error?.message))
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .debugInputPointer(context, viewModel.timeCapsule),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Авторизация",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                modifier = Modifier,
                value = state.email,
                label = { Text("E-mail", style = MaterialTheme.typography.bodyMedium) },
                onValueChange = {
                    viewModel.sendEvent(AuthReducer.AuthEvent.ChangeEmailText(text = it))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = state.emailFieldError?.isNotEmpty() == true,
                supportingText = {
                    ErrorLabel(state.emailFieldError.orEmpty())
                }
            )

            OutlinedTextField(
                modifier = Modifier,
                value = state.password,
                label = { Text("Пароль", style = MaterialTheme.typography.bodyMedium) },
                onValueChange = {
                    viewModel.sendEvent(AuthReducer.AuthEvent.ChangePasswordText(text = it))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (state.isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { viewModel.sendEvent(AuthReducer.AuthEvent.ChangeShowPassword) }) {
                        Icon(
                            painterResource(if (state.isShowPassword) R.drawable.ic_toggle_hide else R.drawable.ic_toggle_show),
                            null
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            RedButton(
                modifier = Modifier
                    .width(TextFieldDefaults.MinWidth)
                    .wrapContentHeight(),
                text = "Войти",
                isEnable = state.email.isEmailValid() && state.password.isNotBlank(),
                onClick = viewModel::auth
            )
        }
    }
}