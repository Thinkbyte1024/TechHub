package dev.thinkbyte1024.techhub.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.thinkbyte1024.techhub.apiService
import dev.thinkbyte1024.techhub.data.model.ProductRequest
import dev.thinkbyte1024.techhub.functions.getProductState
import dev.thinkbyte1024.techhub.view.routes.ViewScreen
import kotlinx.coroutines.runBlocking

@Composable
fun ProductUpdateView(id: String?, navController: NavController) {
    val context = LocalContext.current

    // Mengambil data produk
    val product = getProductState(id)

    product.value.forEach {
        var name by remember {
            mutableStateOf(
                it.name
            )
        }

        var price by remember {
            mutableStateOf(
                it.price.toString()
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column {

                Text(text = "Ubah Produk", style = MaterialTheme.typography.h4)

                Spacer(modifier = Modifier.height(64.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = {
                        Text(text = "Nama barang")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = price,
                    onValueChange = {
                        price = it
                    },
                    label = {
                        Text(text = "Harga barang")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (name.isNotEmpty() && price.isNotEmpty()) {
                            val input = ProductRequest(
                                name = name,
                                price = price.toInt()
                            )

                            postUpdate(id, input)

                            // Kembali ke halaman awal
                            navController.popBackStack(ViewScreen.ProductList.route, inclusive = false, false)

                            Toast.makeText(context, "Produk telah diubah", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Masukkan info produk", Toast.LENGTH_SHORT).show()
                        }
                    },
                    Modifier.align(Alignment.End)
                ) {
                    Text(text = "Ubah")
                }
            }
        }
    }
}

private fun postUpdate(id: String?, request: ProductRequest) = runBlocking<Unit> {
    apiService.updateProducts(request = request, id = id)
}