package pl.ztpproj2.lab2app.features.data.repository

import org.koin.core.annotation.Factory
import pl.ztpproj2.lab2app.features.data.model.local.ProductHistoryCache
import pl.ztpproj2.lab2app.features.domain.database.ProductDao
import pl.ztpproj2.lab2app.features.domain.model.remote.ProductService
import pl.ztpproj2.lab2app.features.domain.repository.ProductHistoryRepository
import pl.ztpproj2.lab2app.features.presentation.model.ProductHistoryDisplayable

@Factory
class ProductHistoryRepositoryImpl(
    private val productService: ProductService,
    private val productDao: ProductDao,
) : ProductHistoryRepository {

    override suspend fun getProductHistory(id: String): List<ProductHistoryDisplayable> =
        runCatching {
            getProductHistoryRemote(id)
        }.getOrElse {
            getProductHistoryFromLocal(id)
        }

    private suspend fun getProductHistoryRemote(id: String): List<ProductHistoryDisplayable> =
        productService.getProductHistory(id).map {
            ProductHistoryDisplayable(it)
        }.also {
            saveProductHistoryToLocal(it)
        }


    private fun saveProductHistoryToLocal(productHistory: List<ProductHistoryDisplayable>) {
        productHistory.map { ProductHistoryCache(it) }
            .toTypedArray()
            .let { productDao.saveProductHistory(*it) }
    }

    private fun getProductHistoryFromLocal(id: String): List<ProductHistoryDisplayable> {
        return productDao.getProductHistory(id)
            .map { it.toProductHistoryDisplayable() }
    }
}