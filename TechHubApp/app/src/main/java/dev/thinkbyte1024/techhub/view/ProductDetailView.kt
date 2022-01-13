package dev.thinkbyte1024.techhub.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.thinkbyte1024.techhub.apiService
import dev.thinkbyte1024.techhub.data.model.Product
import dev.thinkbyte1024.techhub.functions.FormatCurrency
import dev.thinkbyte1024.techhub.functions.getProductState
import dev.thinkbyte1024.techhub.view.routes.ViewScreen
import kotlinx.coroutines.runBlocking

@Composable
fun ProductDetailView(id: String, navController: NavController) {

    // Mengambil data produk
    val product = getProductState(id)

    val (showDialog, setShowDialog) = remember {
        mutableStateOf(false)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        product.value.forEach {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = it.name, style = MaterialTheme.typography.h5)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = FormatCurrency(it.price), style = MaterialTheme.typography.subtitle1)

                    Spacer(modifier = Modifier.height(64.dp))

                    Row(
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Button(onClick = {
                            navController.navigate(ViewScreen.EditProduct.withArgs(id))
                        }) {
                            Text(text = "Edit Data")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = {
                                setShowDialog(true)
                            },
                        ) {
                            Text(text = "Hapus Produk")
                        }
                    }

                    // Tampilkan dialog hapus data
                    DeleteDialog(
                        id = id,
                        showDialog = showDialog,
                        setShowDialog = setShowDialog,
                        navController = navController
                    )
                }
            }
        }
    }
}

// Dialog hapus data
@Composable
private fun DeleteDialog(
    id: String?,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            title = { Text(text = "Hapus data") },
            text = { Text(text = "Apakah anda yakin ingin menghapus data ini?") },
            confirmButton = {
                Button(onClick = {
                    deleteData(id)
                    setShowDialog(false)

                    // Kembali ke halaman awal
                    navController.popBackStack(ViewScreen.ProductList.route, inclusive = false, false)

                    Toast.makeText(context, "Berhasil di hapus", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Hapus")
                }
            },
            dismissButton = {
                Button(onClick = { 
                    setShowDialog(false)
                }) {
                    Text(text = "Batal")
                }
            }
        )

    }
}

// Memanggil fungsi hapus data
private fun deleteData(id: String?) = runBlocking<Unit> {
    apiService.deleteProducts(id)
}