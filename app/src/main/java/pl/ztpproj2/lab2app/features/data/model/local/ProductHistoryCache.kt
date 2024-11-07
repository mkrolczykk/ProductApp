package pl.ztpproj2.lab2app.features.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.ztpproj2.lab2app.features.presentation.model.ProductHistoryDisplayable

@Entity
data class ProductHistoryCache(
    @PrimaryKey
    val id: Int,
    val productId: Int,
    val oldPrice: String,
    val newPrice: String,
    val oldQuantity: Int,
    val newQuantity: Int,
    val changeTimestamp: String,
    val changeDescription: String
) {
    constructor(productHistoryDisplayable: ProductHistoryDisplayable) : this(
        id = productHistoryDisplayable.productHistId?.toInt() ?: 0,
        productId = productHistoryDisplayable.productId,
        oldPrice = productHistoryDisplayable.oldPrice,
        newPrice = productHistoryDisplayable.newPrice,
        oldQuantity = productHistoryDisplayable.oldQuantity,
        newQuantity = productHistoryDisplayable.newQuantity,
        changeTimestamp = productHistoryDisplayable.changeTimestamp ?: "2021-10-10",
        changeDescription = productHistoryDisplayable.changeDescription.orEmpty(),
    )

    fun toProductHistoryDisplayable() = ProductHistoryDisplayable(
        productHistId = id.toLong(),
        productId = productId,
        oldPrice = oldPrice,
        newPrice = newPrice,
        oldQuantity = oldQuantity,
        newQuantity = newQuantity,
        changeTimestamp = changeTimestamp,
        changeDescription = changeDescription,
    )
}