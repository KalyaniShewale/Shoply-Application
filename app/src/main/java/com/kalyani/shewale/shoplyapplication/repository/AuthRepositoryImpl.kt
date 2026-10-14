package com.kalyani.shewale.shoplyapplication.repository

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.kalyani.shewale.shoplyapplication.model.AuthError
import com.kalyani.shewale.shoplyapplication.model.AuthException
import com.kalyani.shewale.shoplyapplication.model.User
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException


class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    private val users get() = firestore.collection("users")

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val firebaseUser = auth.signInWithEmailAndPassword(email, password).await().user
                ?: throw AuthException(AuthError.Unknown)
            val profile = fetchProfile(firebaseUser.uid)
            Result.success(profile.toDomain(firebaseUser.uid, fallbackEmail = email))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e.toAuthException())
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String
    ): Result<User> {
        return try {
            val firebaseUser = auth.createUserWithEmailAndPassword(email, password).await().user
                ?: throw AuthException(AuthError.Unknown)

            val profile = UserDto(name = name, email = email, phone = phone)
            try {
                users.document(firebaseUser.uid).set(profile).await()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // Profile could not be saved: remove the new account so the user can try again
                runCatching { firebaseUser.delete().await() }
                throw e
            }

            Result.success(profile.toDomain(firebaseUser.uid, fallbackEmail = email))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e.toAuthException())
        }
    }

    private suspend fun fetchProfile(uid: String): UserDto? {
        return try {
            users.document(uid).get().await().toObject(UserDto::class.java)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            null
        }
    }
}

private fun Throwable.toAuthException(): AuthException = when (this) {
    is AuthException -> this
    // Weak password must be checked before invalid credentials
    is FirebaseAuthWeakPasswordException -> AuthException(AuthError.WeakPassword)
    is FirebaseAuthUserCollisionException -> AuthException(AuthError.EmailAlreadyInUse)
    is FirebaseAuthInvalidUserException -> AuthException(AuthError.InvalidCredentials)
    is FirebaseAuthInvalidCredentialsException ->
        if (errorCode == "ERROR_INVALID_EMAIL") AuthException(AuthError.InvalidEmail)
        else AuthException(AuthError.InvalidCredentials)
    is FirebaseNetworkException -> AuthException(AuthError.Network)
    is FirebaseTooManyRequestsException -> AuthException(AuthError.TooManyRequests)
    else -> AuthException(AuthError.Unknown)
}