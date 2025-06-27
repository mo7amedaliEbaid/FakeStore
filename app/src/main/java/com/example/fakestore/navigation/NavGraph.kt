package com.example.fakestore.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fakestore.ui.screens.HomeScreen
import com.example.fakestore.ui.screens.ProductDetailsScreen
import com.example.fakestore.ui.screens.ProductsScreen
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Navigation routes for the app
 * These constants define the navigation destinations
 */
object Routes {
    const val HOME = "home"
    const val PRODUCTS = "products"
    const val PRODUCT_DETAILS = "product_details/{productId}"
}

/**
 * Navigation graph that defines all screens and their navigation
 * @param navController Navigation controller for handling navigation
 */
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        // Home screen - entry point of the app
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToProducts = { category ->
                    navController.navigate("${Routes.PRODUCTS}?category=$category")
                }
            )
        }
        
        // Products screen - displays list of products with search
        composable(
            route = "${Routes.PRODUCTS}?category={category}",
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            ProductsScreen(
                category = category,
                onProductClick = { productId ->
                    navController.navigate("${Routes.PRODUCT_DETAILS.replace("{productId}", productId.toString())}")
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Product details screen - shows detailed information about a product
        composable(
            route = Routes.PRODUCT_DETAILS,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            if (productId != null && productId > 0) {
                ProductDetailsScreen(
                    productId = productId,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            } else {
                // Show a simple error message if productId is invalid
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Invalid product ID.")
                }
            }
        }
    }
} 