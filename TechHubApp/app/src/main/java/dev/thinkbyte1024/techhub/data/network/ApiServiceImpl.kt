package dev.thinkbyte1024.techhub.data.network

import dev.thinkbyte1024.techhub.data.model.ProductRequest
import dev.thinkbyte1024.techhub.data.model.Product
import dev.thinkbyte1024.techhub.data.routes.ApiRoutes
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*

class ApiServiceImpl(
    private val client: HttpClient
): ApiService {

    // Index dan baca produk
    override suspend fun getProducts(id: String?): List<Product> {
        return try {
            client.get {
                if (id != null) {
                    url(ApiRoutes.INDEX + "?id=" + id)
                } else {
                    url(ApiRoutes.INDEX)
                }
            }
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: ServerResponseException) {
            // 5xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: Exception) {
            println("Error: ${ex.message}")
            emptyList()
        }
    }

    // Tambahkan produk
    override suspend fun createProducts(request: ProductRequest): Product? {
        return try {
            client.post<Product> {
                url(ApiRoutes.CREATE)
                body = request
            }
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ServerResponseException) {
            // 5xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: Exception) {
            println("Error: ${ex.message}")
            null
        }
    }

    // Update produk
    override suspend fun updateProducts(request: ProductRequest, id: String?): Product? {
        return try {
            client.post<Product> {
                url(ApiRoutes.UPDATE + "?id=" + id)
                body = request
            }
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ServerResponseException) {
            // 5xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: Exception) {
            println("Error: ${ex.message}")
            null
        }
    }

    // Hapus produk
    override suspend fun deleteProducts(id: String?): Boolean {
        return try {
            client.get {
                url(ApiRoutes.DELETE + "?id=" + id)
            }
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            false
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            false
        } catch (ex: ServerResponseException) {
            // 5xx - responses
            println("Error: ${ex.response.status.description}")
            false
        } catch (ex: Exception) {
            println("Error: ${ex.message}")
            false
        }
    }
}