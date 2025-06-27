package com.example.fakestore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fakestore.ui.components.ErrorState
import com.example.fakestore.ui.components.LoadingIndicator
import com.example.fakestore.ui.components.ProductCard
import com.example.fakestore.ui.components.SearchBar
import com.example.fakestore.ui.viewmodel.ProductsViewModel

/**
 * Products screen that displays a list of products with search functionality
 * @param category Category to filter products (empty for all products)
 * @param onProductClick Callback when a product is clicked
 * @param onNavigateBack Callback to navigate back
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    category: String = "",
    onProductClick: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: ProductsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load products by category if category is not empty
    LaunchedEffect(category) {
        if (category.isNotEmpty()) {
            viewModel.loadProductsByCategory(category)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (category.isNotEmpty()) category.capitalize() else "All Products") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                uiState.isLoading -> LoadingIndicator()
                uiState.errorMessage != null -> ErrorState(
                    message = uiState.errorMessage!!,
                    onRetry = { viewModel.retry() }
                )
                else -> Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    SearchBar(
                        query = uiState.searchQuery,
                        onQueryChange = viewModel::onSearchQueryChange
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(uiState.products) { product ->
                            ProductCard(
                                product = product,
                                onClick = {
                                    if (product.id > 0) {
                                        onProductClick(product.id)
                                    } else {
                                        // Optionally show a toast or log an error
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
} 