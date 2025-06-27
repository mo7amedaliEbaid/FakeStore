package com.example.fakestore.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.data.model.Product
import com.example.fakestore.data.repository.ProductRepository
import com.example.fakestore.ui.state.ProductDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Product Details screen
 * Manages the state and business logic for displaying a specific product
 */
class ProductDetailsViewModel : ViewModel() {
    
    // Repository for data operations
    private val repository = ProductRepository()
    
    // Mutable state flow for the product details screen state
    private val _uiState = MutableStateFlow(ProductDetailsState())
    
    // Public state flow that UI can observe
    val uiState: StateFlow<ProductDetailsState> = _uiState.asStateFlow()
    
    /**
     * Load a specific product by its ID
     * @param productId ID of the product to load
     */
    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                if (productId <= 0) {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = "Invalid product ID")
                    }
                    return@launch
                }
                val product = repository.getProductById(productId)
                if (product != null) {
                    _uiState.update {
                        it.copy(product = product, isLoading = false)
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = "Product not found")
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Failed to load product: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Retry loading the product (useful when there's an error)
     * @param productId ID of the product to retry loading
     */
    fun retry(productId: Int) {
        loadProduct(productId)
    }
} 