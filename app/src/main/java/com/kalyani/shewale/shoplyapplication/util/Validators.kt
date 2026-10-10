package com.kalyani.shewale.shoplyapplication.util


import android.util.Patterns
import androidx.annotation.StringRes
import com.kalyani.shewale.shoplyapplication.R

object Validators {

    @StringRes
    fun nameError(name: String): Int? = when {
        name.isBlank() -> R.string.error_name_required
        name.trim().length < 2 -> R.string.error_name_short
        else -> null
    }

    @StringRes
    fun emailError(email: String): Int? = when {
        email.isBlank() -> R.string.error_email_required
        !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> R.string.error_email_invalid
        else -> null
    }

    @StringRes
    fun phoneError(phone: String): Int? = when {
        phone.isBlank() -> R.string.error_phone_required
        phone.filter { it.isDigit() }.length < 7 -> R.string.error_phone_invalid
        else -> null
    }

    @StringRes
    fun passwordError(password: String): Int? = when {
        password.isBlank() -> R.string.error_password_required
        password.length < 6 -> R.string.error_password_short
        else -> null
    }

    @StringRes
    fun confirmPasswordError(password: String, confirm: String): Int? = when {
        confirm.isBlank() -> R.string.error_confirm_required
        password != confirm -> R.string.error_password_mismatch
        else -> null
    }

    fun filterPhoneInput(input: String): String {
        val hasPlus = input.startsWith("+")
        val digits = input.filter { it.isDigit() }.take(15)
        return if (hasPlus) "+$digits" else digits
    }
}