package com.kalyani.shewale.shoplyapplication.usecase

import com.kalyani.shewale.shoplyapplication.model.User
import com.kalyani.shewale.shoplyapplication.repository.AuthRepository
import javax.inject.Inject


class IsLoggedInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean = repository.isLoggedIn()
}

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> =
        repository.login(email.trim(), password)
}

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.logout()
}