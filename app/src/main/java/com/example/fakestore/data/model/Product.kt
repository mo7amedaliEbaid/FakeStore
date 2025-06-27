package com.example.fakestore.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a product from the FakeStore API
 * This class maps the JSON response structure to Kotlin objects
 */
data class Product(
    val id: Int,                    // Unique product identifier
    val title: String,              // Product name/title
    val image: String,              // URL to product image
    val price: Double,              // Product price
    val description: String,        // Product description
    val brand: String,              // Product brand
    val model: String,              // Product model
    val color: String,              // Product color
    val category: String,           // Product category (e.g., audio, gaming, mobile, tv)
    val discount: Int? = null,      // Discount percentage (optional)
    val popular: Boolean? = null,   // Whether product is popular (optional)
    @SerializedName("onSale")
    val onSale: Boolean? = null     // Whether product is on sale (optional)
) 