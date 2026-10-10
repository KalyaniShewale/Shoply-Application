package com.kalyani.shewale.shoplyapplication.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalyani.shewale.shoplyapplication.R
import com.kalyani.shewale.shoplyapplication.ui.components.AppTextField
import com.kalyani.shewale.shoplyapplication.ui.components.AuthFooter
import com.kalyani.shewale.shoplyapplication.ui.components.AuthHeader
import com.kalyani.shewale.shoplyapplication.ui.components.ErrorBanner
import com.kalyani.shewale.shoplyapplication.ui.components.PrimaryButton
import com.kalyani.shewale.shoplyapplication.ui.theme.ShoplyApplicationTheme
import com.kalyani.shewale.shoplyapplication.util.Validators

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onRegisterClick: (name: String, email: String, phone: String, password: String) -> Unit = { _, _, _, _ -> },
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    @StringRes var nameError by rememberSaveable { mutableStateOf<Int?>(null) }
    @StringRes var emailError by rememberSaveable { mutableStateOf<Int?>(null) }
    @StringRes var phoneError by rememberSaveable { mutableStateOf<Int?>(null) }
    @StringRes var passwordError by rememberSaveable { mutableStateOf<Int?>(null) }
    @StringRes var confirmError by rememberSaveable { mutableStateOf<Int?>(null) }

    val focusManager = LocalFocusManager.current

    fun submit() {
        nameError = Validators.nameError(name)
        emailError = Validators.emailError(email)
        phoneError = Validators.phoneError(phone)
        passwordError = Validators.passwordError(password)
        confirmError = Validators.confirmPasswordError(password, confirmPassword)

        val valid = listOf(nameError, emailError, phoneError, passwordError, confirmError)
            .all { it == null }
        if (valid) {
            onRegisterClick(name.trim(), email.trim(), phone.trim(), password)
        }
    }

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(onClick = onBackClick, enabled = !isLoading) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }

            AuthHeader(
                title = stringResource(R.string.register_title),
                subtitle = stringResource(R.string.register_subtitle),
                showLogo = false,
                horizontalAlignment = Alignment.Start
            )

            Spacer(Modifier.height(8.dp))

            if (errorMessage != null) {
                ErrorBanner(message = errorMessage)
            }

            AppTextField(
                value = name,
                onValueChange = { name = it; nameError = null },
                label = stringResource(R.string.register_name_label),
                leadingIcon = Icons.Outlined.Person,
                errorMessage = nameError?.let { stringResource(it) },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )

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
                value = phone,
                onValueChange =  { phone = Validators.filterPhoneInput(it); phoneError = null },
                label = stringResource(R.string.register_phone_label),
                leadingIcon = Icons.Outlined.Phone,
                keyboardType = KeyboardType.Phone,
                errorMessage = phoneError?.let { stringResource(it) },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            AppTextField(
                value = password,
                onValueChange = { password = it; passwordError = null },
                label = stringResource(R.string.login_password_label),
                placeholder = stringResource(R.string.register_password_hint),
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true,
                errorMessage = passwordError?.let { stringResource(it) },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            AppTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it; confirmError = null },
                label = stringResource(R.string.register_confirm_label),
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true,
                errorMessage = confirmError?.let { stringResource(it) },
                enabled = !isLoading,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    submit()
                }),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(4.dp))

            PrimaryButton(
                text = stringResource(R.string.register_button),
                onClick = {
                    focusManager.clearFocus()
                    submit()
                },
                isLoading = isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            AuthFooter(
                prompt = stringResource(R.string.register_have_account),
                actionText = stringResource(R.string.register_log_in),
                onActionClick = onLoginClick
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    ShoplyApplicationTheme { RegisterScreen() }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenLoadingPreview() {
    ShoplyApplicationTheme { RegisterScreen(isLoading = true) }
}