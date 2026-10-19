package com.kalyani.shewale.shoplyapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalyani.shewale.shoplyapplication.model.Product
import com.kalyani.shewale.shoplyapplication.usecase.ObserveFavoritesUseCase
import com.kalyani.shewale.shoplyapplication.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    observeFavorites: ObserveFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val favorites: StateFlow<List<Product>> = observeFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun toggle(product: Product) {
        viewModelScope.launch { toggleFavoriteUseCase(product) }
    }
}