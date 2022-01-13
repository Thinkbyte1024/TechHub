package dev.thinkbyte1024.techhub.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.thinkbyte1024.techhub.apiService
import dev.thinkbyte1024.techhub.data.model.Product
import dev.thinkbyte1024.techhub.functions.FormatCurrency
import dev.thinkbyte1024.techhub.functions.getProductState
import dev.thinkbyte1024.techhub.view.routes.ViewScreen
import java.text.NumberFormat
import java.util.*

@Composable
fun ProductListView(navController: NavController) {

    // Mengambil daftar produk
    val product = getProductState()

    Column {
        Row(Modifier.padding(16.dp)) {
            Button(onClick = { navController.navigate(ViewScreen.AddProduct.route) }) {
                Text(text = "Tambah Produk")
            }
        }

        Box {
            LazyColumn {
                items(product.value) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { navController.navigate(ViewScreen.DetailProduct.withArgs(it.id)) },
                        elevation = 8.dp
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(text = it.name)
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(text = FormatCurrency(it.price))
                        }
                    }
                }
            }
        }
    }
}