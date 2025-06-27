# FakeStore - Jetpack Compose Learning App

A comprehensive Android app built with Jetpack Compose that demonstrates modern Android development practices, MVVM architecture, and integration with REST APIs.

## 🚀 Features

- **Home Screen**: Browse products by category (Mobile, Audio, Gaming, TV, All Products)
- **Products Screen**: View product list with search functionality
- **Product Details Screen**: Detailed view of individual products
- **Search**: Real-time search across product titles, brands, and categories
- **Modern UI**: Beautiful Material Design 3 interface with Jetpack Compose
- **Error Handling**: Graceful error states with retry functionality
- **Loading States**: Smooth loading indicators throughout the app

## 📱 Screenshots

The app includes three main screens:
1. **Home Screen**: Category selection grid
2. **Products Screen**: Product list with search bar
3. **Product Details Screen**: Detailed product information

## 🛠️ Tech Stack

- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit + Gson
- **Image Loading**: Coil
- **Navigation**: Navigation Compose
- **State Management**: StateFlow
- **Coroutines**: For asynchronous operations
- **Material Design**: Material 3

## 📋 Prerequisites

- Android Studio Hedgehog or later
- Android SDK 24+ (API level 24)
- Kotlin 2.0.0+
- Internet connection (for API calls)

## 🏃‍♂️ How to Run

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd FakeStore
   ```

2. **Open in Android Studio**:
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the FakeStore folder and select it

3. **Sync the project**:
   - Wait for Gradle sync to complete
   - If prompted, update any dependencies

4. **Run the app**:
   - Connect an Android device or start an emulator
   - Click the "Run" button (green play icon) in Android Studio
   - The app will install and launch on your device

## 🏗️ Project Structure

```
app/src/main/java/com/example/fakestore/
├── data/
│   ├── model/
│   │   ├── Product.kt              # Product data class
│   │   └── ApiResponse.kt          # API response wrapper
│   ├── remote/
│   │   ├── ApiService.kt           # Retrofit API interface
│   │   └── NetworkModule.kt        # Retrofit setup
│   └── repository/
│       └── ProductRepository.kt    # Data operations
├── navigation/
│   └── NavGraph.kt                 # Navigation setup
├── ui/
│   ├── components/
│   │   └── CommonComponents.kt     # Reusable UI components
│   ├── screens/
│   │   ├── HomeScreen.kt           # Category selection screen
│   │   ├── ProductsScreen.kt       # Product list screen
│   │   └── ProductDetailsScreen.kt # Product details screen
│   ├── state/
│   │   └── UiState.kt              # UI state classes
│   ├── viewmodel/
│   │   ├── ProductsViewModel.kt    # Products screen logic
│   │   └── ProductDetailsViewModel.kt # Product details logic
│   └── theme/
│       ├── Color.kt                # App colors
│       ├── Theme.kt                # App theme
│       └── Type.kt                 # Typography
└── MainActivity.kt                 # App entry point
```

## 📚 Learning Guide

### 1. **Jetpack Compose Basics**

#### What is Jetpack Compose?
Jetpack Compose is Android's modern toolkit for building native UI. It simplifies and accelerates UI development with less code, powerful tools, and intuitive Kotlin APIs.

#### Key Concepts:
- **Composable Functions**: Functions marked with `@Composable` that describe UI
- **State Management**: Using `StateFlow` and `collectAsState()` for reactive UI
- **Material Design**: Built-in Material 3 components and theming

#### Example from our app:
```kotlin
@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        // UI content
    }
}
```

### 2. **MVVM Architecture**

#### Model-View-ViewModel Pattern:
- **Model**: Data layer (Product, ApiResponse, Repository)
- **View**: UI layer (Compose screens)
- **ViewModel**: Business logic and state management

#### Benefits:
- Separation of concerns
- Testability
- Lifecycle awareness
- State preservation

#### Example from our app:
```kotlin
// ViewModel (Business Logic)
class ProductsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProductsScreenState())
    val uiState: StateFlow<ProductsScreenState> = _uiState.asStateFlow()
    
    fun onSearchQueryChange(query: String) {
        // Update search logic
    }
}

// View (UI)
@Composable
fun ProductsScreen(viewModel: ProductsViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    // Display UI based on state
}
```

### 3. **Networking with Retrofit**

#### API Service Interface:
```kotlin
interface ApiService {
    @GET("products")
    suspend fun getAllProducts(): ApiResponse
    
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}
```

#### Repository Pattern:
```kotlin
class ProductRepository {
    private val apiService = NetworkModule.apiService
    
    suspend fun getAllProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            apiService.getAllProducts().products
        }
    }
}
```

### 4. **Navigation with Navigation Compose**

#### Navigation Setup:
```kotlin
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) { HomeScreen() }
        composable(Routes.PRODUCTS) { ProductsScreen() }
        composable(Routes.PRODUCT_DETAILS) { ProductDetailsScreen() }
    }
}
```

### 5. **State Management with StateFlow**

#### State Classes:
```kotlin
data class ProductsScreenState(
    val products: List<Product> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
```

#### StateFlow Usage:
```kotlin
// In ViewModel
private val _uiState = MutableStateFlow(ProductsScreenState())
val uiState: StateFlow<ProductsScreenState> = _uiState.asStateFlow()

// In Composable
val uiState by viewModel.uiState.collectAsState()
```

## 🔧 API Endpoints Used

The app uses the following FakeStore API endpoints:

1. **Get All Products**: `https://fakestoreapi.in/api/products`
2. **Get Product by ID**: `https://fakestoreapi.in/api/products/{id}`
3. **Get Products by Category**: `https://fakestoreapi.in/api/products/category?type={category}`

## 🎯 Key Learning Points

### 1. **Compose UI Patterns**
- **LazyColumn/LazyVerticalGrid**: For efficient list display
- **Scaffold**: For consistent screen structure
- **TopAppBar**: For navigation and titles
- **Card**: For content containers

### 2. **State Management**
- **StateFlow**: For reactive state updates
- **collectAsState()**: For observing state in Compose
- **LaunchedEffect**: For side effects in Compose

### 3. **Error Handling**
- **Try-catch blocks**: In repository layer
- **Error states**: In UI for user feedback
- **Retry functionality**: For failed operations

### 4. **Performance**
- **Coil**: Efficient image loading
- **Lazy loading**: For large lists
- **Coroutines**: For background operations

## 🚨 Common Issues & Solutions

### 1. **Build Errors**
- **Gradle sync failed**: Check internet connection and try "File > Invalidate Caches"
- **Missing dependencies**: Ensure all dependencies are properly declared in `build.gradle.kts`

### 2. **Runtime Errors**
- **Network errors**: Check internet connection and API availability
- **Navigation errors**: Ensure all routes are properly defined

### 3. **UI Issues**
- **Layout problems**: Check modifier usage and constraints
- **State not updating**: Verify StateFlow and collectAsState usage

## 📖 Additional Resources

### Official Documentation:
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Retrofit](https://square.github.io/retrofit/)

### Learning Path:
1. **Start with**: Basic Compose UI components
2. **Learn**: State management with StateFlow
3. **Practice**: Navigation between screens
4. **Implement**: Network calls with Retrofit
5. **Master**: MVVM architecture patterns

## 🤝 Contributing

Feel free to contribute to this learning project:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## 📄 License

This project is for educational purposes. Feel free to use and modify for learning Jetpack Compose and Android development.

---

**Happy Learning! 🎉**

This app demonstrates modern Android development practices and serves as a great starting point for learning Jetpack Compose, MVVM architecture, and Android app development. 