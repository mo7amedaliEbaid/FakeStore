package com.example.fakestore.data.remote

import com.example.fakestore.data.model.ApiResponse
import com.example.fakestore.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit service interface for FakeStore API
 * This interface defines all the API endpoints we'll use in our app
 */
interface ApiService {
    
    /**
     * Get all products from the API
     * Endpoint: https://fakestoreapi.in/api/products
     * Returns: ApiResponse containing list of all products
     */
    @GET("products")
    suspend fun getAllProducts(): ApiResponse
    
    /**
     * Get a specific product by ID
     * Endpoint: https://fakestoreapi.in/api/products/{id}
     * Returns: ProductResponse containing a single Product object
     */
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): com.example.fakestore.data.model.ProductResponse
    
    /**
     * Get products by category
     * Endpoint: https://fakestoreapi.in/api/products/category?type={category}
     * Returns: ApiResponse containing list of products in the specified category
     */
    @GET("products/category")
    suspend fun getProductsByCategory(@Query("type") category: String): ApiResponse
} 