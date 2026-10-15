package com.kalyani.shewale.shoplyapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.kalyani.shewale.shoplyapplication.navigation.AppRoute
import com.kalyani.shewale.shoplyapplication.usecase.IsLoggedInUseCase
import com.kalyani.shewale.shoplyapplication.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    isLoggedInUseCase: IsLoggedInUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    // Decided once when the app starts: logged in users skip the login screen
    val startDestination: AppRoute =
        if (isLoggedInUseCase()) AppRoute.Home else AppRoute.Login

    fun logout() = logoutUseCase()
}