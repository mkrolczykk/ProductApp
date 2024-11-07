package pl.ztpproj2.lab2app.features.data.cache

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import org.koin.core.annotation.Single
import pl.ztpproj2.lab2app.features.domain.cache.ResponseCacheControl

const val timeCache = 3000L

@Single
class ProductsResponseCacheControl(
    private val sharedPreferences: SharedPreferences,
    private val editor: Editor
) : ResponseCacheControl {

    override fun updateCacheControl() {
        editor.putLong("cache-control", System.currentTimeMillis())
        editor.commit()
    }

    override fun isCached(): Boolean {
        val cache = sharedPreferences.getLong("cache-control", 0).takeIf { it > 0 }

        return cache?.let {
            System.currentTimeMillis() < cache + timeCache
        } ?: false
    }
}