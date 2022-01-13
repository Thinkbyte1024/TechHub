package dev.thinkbyte1024.techhub.data.network

import dev.thinkbyte1024.techhub.data.model.ProductRequest
import dev.thinkbyte1024.techhub.data.model.Product
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

interface ApiService {
    suspend fun getProducts(id: String? = null): List<Product>
    suspend fun createProducts(request: ProductRequest): Product?
    suspend fun updateProducts(request: ProductRequest, id: String?): Product?
    suspend fun deleteProducts(id: String?): Boolean

    companion object {
        fun create(): ApiServiceImpl {
            return ApiServiceImpl(
                client = HttpClient(Android) {

                    install(Logging) {
                        level = LogLevel.ALL
                    }

                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }

                    install(HttpTimeout) {
                        requestTimeoutMillis = 15000L
                        connectTimeoutMillis = 15000L
                        socketTimeoutMillis = 15000L
                    }

                    defaultRequest {
                        if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }
                }
            )
        }
    }
}