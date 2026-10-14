package com.kalyani.shewale.shoplyapplication.repository

import com.kalyani.shewale.shoplyapplication.model.User


data class UserDto(
    val name: String = "",
    val email: String = "",
    val phone: String = ""
)

fun UserDto?.toDomain(uid: String, fallbackEmail: String) = User(
    uid = uid,
    name = this?.name.orEmpty(),
    email = this?.email ?: fallbackEmail,
    phone = this?.phone.orEmpty()
)