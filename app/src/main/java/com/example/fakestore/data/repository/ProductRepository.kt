package com.example.fakestore.data.repository

import com.example.fakestore.data.model.Product
import com.example.fakestore.data.remote.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class that handles all product-related data operations
 * This follows the Repository pattern to provide a clean API for data access
 */
class ProductRepository {
    
    // API service instance for making network calls
    private val apiService = NetworkModule.apiService
    
    /**
     * Get all products from the API
     * @return List of all products
     */
    suspend fun getAllProducts(): List<Product> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllProducts()
            response.products
        } catch (e: Exception) {
            // In a real app, you might want to log this error or handle it differently
            emptyList()
        }
    }
    
    /**
     * Get a specific product by its ID
     * @param id Product ID
     * @return Product object or null if not found
     */
    suspend fun getProductById(id: Int): Product? = withContext(Dispatchers.IO) {
        try {
            apiService.getProductById(id).product
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Get products filtered by category
     * @param category Category name (e.g., "mobile", "audio", "gaming", "tv")
     * @return List of products in the specified category
     */
    suspend fun getProductsByCategory(category: String): List<Product> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getProductsByCategory(category)
            response.products
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Search products by title (client-side filtering)
     * @param products List of products to search in
     * @param query Search query
     * @return Filtered list of products matching the query
     */
    fun searchProducts(products: List<Product>, query: String): List<Product> {
        return if (query.isBlank()) {
            products
        } else {
            products.filter { product ->
                product.title.contains(query, ignoreCase = true) ||
                product.brand.contains(query, ignoreCase = true) ||
                product.category.contains(query, ignoreCase = true)
            }
        }
    }
} 