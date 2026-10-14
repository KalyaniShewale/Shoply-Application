package com.kalyani.shewale.shoplyapplication.usecase


import com.kalyani.shewale.shoplyapplication.model.User
import com.kalyani.shewale.shoplyapplication.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        phone: String,
        password: String
    ): Result<User> = repository.register(name.trim(), email.trim(), phone.trim(), password)
}