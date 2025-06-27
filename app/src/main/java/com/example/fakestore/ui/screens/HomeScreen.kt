package com.example.fakestore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Data class representing a category option
 */
data class CategoryOption(
    val name: String,
    val displayName: String,
    val icon: ImageVector,
    val color: androidx.compose.ui.graphics.Color
)

/**
 * Home screen that displays category options for browsing products
 * @param onNavigateToProducts Callback when a category is selected
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProducts: (String) -> Unit
) {
    // Define available categories with their icons and colors
    val categories = listOf(
        CategoryOption(
            name = "mobile",
            displayName = "Mobile",
            icon = Icons.Default.Phone,
            color = MaterialTheme.colorScheme.primary
        ),
        CategoryOption(
            name = "audio",
            displayName = "Audio",
            icon = Icons.Default.PlayArrow,
            color = MaterialTheme.colorScheme.secondary
        ),
        CategoryOption(
            name = "gaming",
            displayName = "Gaming",
            icon = Icons.Default.ThumbUp,
            color = MaterialTheme.colorScheme.tertiary
        ),
        CategoryOption(
            name = "tv",
            displayName = "TV",
            icon = Icons.Default.MoreVert,
            color = MaterialTheme.colorScheme.error
        ),
        CategoryOption(
            name = "",
            displayName = "All Products",
            icon = Icons.Default.ShoppingCart,
            color = MaterialTheme.colorScheme.primary
        )
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "FakeStore",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Welcome message
            Text(
                text = "Welcome to FakeStore!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Choose a category to browse products",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Category grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    CategoryCard(
                        category = category,
                        onClick = { onNavigateToProducts(category.name) }
                    )
                }
            }
        }
    }
}

/**
 * Category card component
 * Displays a category option with icon and name
 */
@Composable
fun CategoryCard(
    category: CategoryOption,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Category icon
            Icon(
                imageVector = category.icon,
                contentDescription = category.displayName,
                modifier = Modifier.size(32.dp),
                tint = category.color
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Category name
            Text(
                text = category.displayName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
} 