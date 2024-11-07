package pl.ztpproj2.lab2app.features.domain.cache

interface ResponseCacheControl {
    fun updateCacheControl()
    fun isCached(): Boolean
}