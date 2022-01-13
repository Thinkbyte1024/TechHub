package dev.thinkbyte1024.techhub.functions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import dev.thinkbyte1024.techhub.apiService
import dev.thinkbyte1024.techhub.data.model.Product

@Composable
fun getProductState(id: String? = null): State<List<Product>> {
    return produceState(
        initialValue = emptyList(),
        producer = {
            value = if (id != null) {
                apiService.getProducts(id)
            } else {
                apiService.getProducts()
            }
        }
    )
}