package com.kalyani.shewale.shoplyapplication.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalyani.shewale.shoplyapplication.ui.components.AppTextField
import com.kalyani.shewale.shoplyapplication.ui.components.AuthFooter
import com.kalyani.shewale.shoplyapplication.ui.components.AuthHeader
import com.kalyani.shewale.shoplyapplication.ui.components.ErrorBanner
import com.kalyani.shewale.shoplyapplication.ui.components.OrDivider
import com.kalyani.shewale.shoplyapplication.ui.components.PrimaryButton
import com.kalyani.shewale.shoplyapplication.ui.components.SocialButton
import com.kalyani.shewale.shoplyapplication.ui.theme.ShoplyApplicationTheme
import com.kalyani.shewale.shoplyapplication.util.Validators
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.kalyani.shewale.shoplyapplication.R

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onLoginClick: (email: String, password: String) -> Unit = { _, _ -> },
    onGoogleClick: () -> Unit = {},
    onGithubClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    @StringRes var emailError by rememberSaveable { mutableStateOf<Int?>(null) }
    @StringRes var passwordError by rememberSaveable { mutableStateOf<Int?>(null) }

    val focusManager = LocalFocusManager.current

    fun submit() {
        emailError = Validators.emailError(email)
        passwordError = Validators.passwordError(password)
        if (emailError == null && passwordError == null) {
            onLoginClick(email.trim(), password)
        }
    }

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AuthHeader(
                title = stringResource(R.string.login_title),
                subtitle = stringResource(R.string.login_subtitle)
            )

            Spacer(Modifier.height(12.dp))

            if (errorMessage != null) {
                ErrorBanner(message = errorMessage)
            }

            AppTextField(
                value = email,
                onValueChange = { email = it; emailError = null },
                label = stringResource(R.string.login_email_label),
                leadingIcon = Icons.Outlined.Email,
                keyboardType = KeyboardType.Email,
                errorMessage = emailError?.let { stringResource(it) },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            AppTextField(
                value = password,
                onValueChange = { password = it; passwordError = null },
                label = stringResource(R.string.login_password_label),
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true,
                errorMessage = passwordError?.let { stringResource(it) },
                enabled = !isLoading,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    submit()
                }),
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(
                onClick = onForgotPasswordClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.login_forgot_password))
            }

            PrimaryButton(
                text = stringResource(R.string.login_button),
                onClick = {
                    focusManager.clearFocus()
                    submit()
                },
                isLoading = isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            OrDivider(
                text = stringResource(R.string.login_or_continue),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SocialButton(
                    text = stringResource(R.string.login_google),
                    onClick = onGoogleClick,
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f)
                )
                SocialButton(
                    text = stringResource(R.string.login_github),
                    onClick = onGithubClick,
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f)
                )
            }

            AuthFooter(
                prompt = stringResource(R.string.login_new_here),
                actionText = stringResource(R.string.login_create_account),
                onActionClick = onRegisterClick
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    ShoplyApplicationTheme { LoginScreen() }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenErrorPreview() {
    ShoplyApplicationTheme {
        LoginScreen(errorMessage = stringResource(R.string.error_login_failed))
    }
}