package com.kalyani.shewale.shoplyapplication.auth


import androidx.annotation.StringRes
import com.kalyani.shewale.shoplyapplication.R
import com.kalyani.shewale.shoplyapplication.model.AuthError
import com.kalyani.shewale.shoplyapplication.model.AuthException

@StringRes
fun AuthError.toMessageRes(): Int = when (this) {
    AuthError.InvalidCredentials -> R.string.auth_error_invalid_credentials
    AuthError.InvalidEmail -> R.string.auth_error_invalid_email
    AuthError.EmailAlreadyInUse -> R.string.auth_error_email_in_use
    AuthError.WeakPassword -> R.string.auth_error_weak_password
    AuthError.Network -> R.string.auth_error_network
    AuthError.TooManyRequests -> R.string.auth_error_too_many_requests
    AuthError.Unknown -> R.string.auth_error_unknown
}

fun Throwable?.toAuthError(): AuthError =
    (this as? AuthException)?.error ?: AuthError.Unknown