package pl.ztpproj2.lab2app.features.domain.model.remote

import pl.ztpproj2.lab2app.features.data.model.remote.ProductDtoRequest
import pl.ztpproj2.lab2app.features.data.model.remote.ProductHistoryResponse
import pl.ztpproj2.lab2app.features.data.model.remote.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    @GET("/api/v1/products")
    suspend fun getProducts(): List<ProductResponse>

    @GET("/api/v1/products/{id}")
    suspend fun getProduct(@Path("id") id: String): ProductResponse

    @GET("/api/v1/products/{id}/history")
    suspend fun getProductHistory(@Path("id") id: String): List<ProductHistoryResponse>

    @POST("/api/v1/products")
    suspend fun addProduct(@Body productDtoRequest: ProductDtoRequest): Response<Unit>

    @PUT("/api/v1/products/{id}")
    suspend fun updateProduct(
        @Path("id") id: String,
        @Body productDtoRequest: ProductDtoRequest
    ): Response<Unit>

    @DELETE("/api/v1/products/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<Unit>
}