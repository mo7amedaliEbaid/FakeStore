package com.example.fakestore.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.data.model.Product
import com.example.fakestore.data.repository.ProductRepository
import com.example.fakestore.ui.state.ProductsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Products screen
 * Manages the state and business logic for displaying and searching products
 */
class ProductsViewModel : ViewModel() {
    
    // Repository for data operations
    private val repository = ProductRepository()
    
    // Mutable state flow for the products screen state
    private val _uiState = MutableStateFlow(ProductsScreenState())
    
    // Public state flow that UI can observe
    val uiState: StateFlow<ProductsScreenState> = _uiState.asStateFlow()
    
    // All products loaded from API
    private var allProducts: List<Product> = emptyList()
    
    init {
        // Load products when ViewModel is created
        loadProducts()
    }
    
    /**
     * Load all products from the API
     */
    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            try {
                allProducts = repository.getAllProducts()
                updateDisplayedProducts()
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load products: ${e.message}"
                    )
                }
            }
        }
    }
    
    /**
     * Update the search query and filter products
     * @param query Search query string
     */
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        updateDisplayedProducts()
    }
    
    /**
     * Update the displayed products based on current search query
     */
    private fun updateDisplayedProducts() {
        val filteredProducts = repository.searchProducts(allProducts, _uiState.value.searchQuery)
        _uiState.update { 
            it.copy(
                products = filteredProducts,
                isLoading = false
            )
        }
    }
    
    /**
     * Load products by category
     * @param category Category name
     */
    fun loadProductsByCategory(category: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            try {
                allProducts = repository.getProductsByCategory(category)
                updateDisplayedProducts()
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load products: ${e.message}"
                    )
                }
            }
        }
    }
    
    /**
     * Retry loading products (useful when there's an error)
     */
    fun retry() {
        loadProducts()
    }
} 