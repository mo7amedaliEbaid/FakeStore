package com.example.fakestore.ui.state

import com.example.fakestore.data.model.Product

/**
 * Sealed class representing different UI states
 * This helps us handle loading, success, and error states properly
 */
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

/**
 * Data class representing the state of the products screen
 * Contains the list of products and search query
 */
data class ProductsScreenState(
    val products: List<Product> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

/**
 * Data class representing the state of the product details screen
 * Contains the selected product and loading/error states
 */
data class ProductDetailsState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) 