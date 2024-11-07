package pl.ztpproj2.lab2app.features.domain.repository

import pl.ztpproj2.lab2app.features.presentation.model.ProductHistoryDisplayable

interface ProductHistoryRepository {
    suspend fun getProductHistory(id: String): List<ProductHistoryDisplayable>
}