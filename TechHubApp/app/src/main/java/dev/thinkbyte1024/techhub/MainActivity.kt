package dev.thinkbyte1024.techhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.thinkbyte1024.techhub.data.network.ApiService
import dev.thinkbyte1024.techhub.ui.theme.TechHubTheme
import dev.thinkbyte1024.techhub.view.ProductAddView
import dev.thinkbyte1024.techhub.view.ProductDetailView
import dev.thinkbyte1024.techhub.view.ProductListView
import dev.thinkbyte1024.techhub.view.ProductUpdateView
import dev.thinkbyte1024.techhub.view.routes.ViewScreen

val apiService by lazy {
    ApiService.create()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechHubTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainNavigation()
                }
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = ViewScreen.ProductList.route) {

        // Halaman daftar produk
        composable(ViewScreen.ProductList.route) {
            ProductListView(navController = navController)
        }

        // Halaman detil produk
        composable(
            route = ViewScreen.DetailProduct.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getString("id")?.let { ProductDetailView(id = it, navController = navController) }
        }

        // Halaman tambah produk
        composable(ViewScreen.AddProduct.route) {
            ProductAddView(navController = navController)
        }

        // Halaman update produk
        composable(
            route = ViewScreen.EditProduct.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getString("id").let { ProductUpdateView(id = it, navController) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TechHubTheme {
        MainNavigation()
    }
}