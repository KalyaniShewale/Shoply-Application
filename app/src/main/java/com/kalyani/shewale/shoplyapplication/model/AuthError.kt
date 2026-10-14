package com.kalyani.shewale.shoplyapplication.model

sealed class AuthError {
    data object InvalidCredentials : AuthError()
    data object InvalidEmail : AuthError()
    data object EmailAlreadyInUse : AuthError()
    data object WeakPassword : AuthError()
    data object Network : AuthError()
    data object TooManyRequests : AuthError()
    data object Unknown : AuthError()
}

class AuthException(val error: AuthError) : Exception(error.toString())