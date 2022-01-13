package dev.thinkbyte1024.techhub.data.model

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ProductRequest(
    val name: String,
    val price: Int
)
