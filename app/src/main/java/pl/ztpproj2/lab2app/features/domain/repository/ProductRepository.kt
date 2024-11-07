package pl.ztpproj2.lab2app.features.domain.repository

import pl.ztpproj2.lab2app.features.data.cache.ProductsResponseCacheControl
import pl.ztpproj2.lab2app.features.presentation.model.ProductDisplayable

interface ProductRepository {
    val productsResponseCacheControl: ProductsResponseCacheControl
    suspend fun getProduct(id: String): ProductDisplayable?
    suspend fun getProducts(): List<ProductDisplayable>
    suspend fun deleteProduct(id: String): Boolean
}