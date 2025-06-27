package com.example.fakestore.data.model

/**
 * Data class representing the API response structure
 * The FakeStore API returns a wrapper object with status, message, and products
 */
data class ApiResponse(
    val status: String,             // API response status (e.g., "SUCCESS")
    val message: String,            // API response message
    val products: List<Product>     // List of products
) 