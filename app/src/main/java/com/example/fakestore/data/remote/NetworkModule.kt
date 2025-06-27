package com.example.fakestore.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network module that provides Retrofit instance and API service
 * This object sets up the networking layer for our app
 */
object NetworkModule {
    
    // Base URL for the FakeStore API
    private const val BASE_URL = "https://fakestoreapi.in/api/"
    
    /**
     * Retrofit instance configured with:
     * - Base URL: https://fakestoreapi.in/api/
     * - Gson converter for JSON parsing
     */
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    /**
     * API service instance created from the Retrofit instance
     * This is what we'll use to make API calls
     */
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
} 