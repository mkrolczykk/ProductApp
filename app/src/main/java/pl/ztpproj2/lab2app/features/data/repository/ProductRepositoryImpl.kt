package pl.ztpproj2.lab2app.features.data.repository

import org.koin.core.annotation.Factory
import pl.ztpproj2.lab2app.features.data.cache.ProductsResponseCacheControl
import pl.ztpproj2.lab2app.features.data.model.local.ProductCache
import pl.ztpproj2.lab2app.features.domain.database.ProductDao
import pl.ztpproj2.lab2app.features.domain.model.remote.ProductService
import pl.ztpproj2.lab2app.features.domain.repository.ProductRepository
import pl.ztpproj2.lab2app.features.presentation.model.ProductDisplayable

@Factory
class ProductRepositoryImpl(
    private val productService: ProductService,
    private val productDao: ProductDao,
    override val productsResponseCacheControl: ProductsResponseCacheControl,
) : ProductRepository {
    override suspend fun getProduct(id: String): ProductDisplayable? =
        runCatching {
            getProductFromRemote(id)
        }.getOrNull()

    override suspend fun getProducts(): List<ProductDisplayable> =
        runCatching {
            if (productsResponseCacheControl.isCached()) throw Exception("Data are just loaded")
            getProductsFromRemote()
        }.getOrElse {
            getProductsFromLocal()
        }


    override suspend fun deleteProduct(id: String): Boolean =
        runCatching {
            productService.deleteProduct(id = id)
        }.isSuccess.also { productsResponseCacheControl.updateCacheControl() }


    private suspend fun getProductsFromRemote(): List<ProductDisplayable> =
        productService.getProducts().map {
            ProductDisplayable(it)
        }.also {
            productsResponseCacheControl.updateCacheControl()
            saveProductsToLocal(it)
        }

    private suspend fun getProductFromRemote(id: String): ProductDisplayable =
        ProductDisplayable(productService.getProduct(id)).also { productsResponseCacheControl.updateCacheControl() }

    private fun saveProductsToLocal(products: List<ProductDisplayable>) {
        products.map { ProductCache(it) }
            .toTypedArray()
            .let { productDao.saveProducts(*it) }
    }

    private fun getProductsFromLocal(): List<ProductDisplayable> {
        return productDao.getProducts()
            .map { it.toProductDisplayable() }
    }
}