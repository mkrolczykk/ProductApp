package pl.ztpproj2.lab2app.features.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.ztpproj2.lab2app.features.data.model.local.ProductCache
import pl.ztpproj2.lab2app.features.data.model.local.ProductHistoryCache

@Database(
    entities = [ProductCache::class, ProductHistoryCache::class],
    version = 1, exportSchema = false
)

@TypeConverters(ListConverter::class)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun productDao(): ProductDao
}