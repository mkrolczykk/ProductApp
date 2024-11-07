package pl.ztpproj2.lab2app.features.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.ztpproj2.lab2app.features.data.model.local.ProductCache
import pl.ztpproj2.lab2app.features.data.model.local.ProductHistoryCache

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductCache")
    fun getProducts(): List<ProductCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProducts(vararg productCached: ProductCache)

    @Query("SELECT * FROM ProductHistoryCache Where productId = :id")
    fun getProductHistory(id: String): List<ProductHistoryCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProductHistory(vararg productHistoryCache: ProductHistoryCache)
}