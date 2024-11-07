package pl.ztpproj2.lab2app.features.presentation.model

import pl.ztpproj2.lab2app.features.data.model.remote.ProductHistoryResponse

data class ProductHistoryDisplayable(
    val productHistId: Long?,
    val productId: Int,
    val oldPrice: String,
    val newPrice: String,
    val oldQuantity: Int,
    val newQuantity: Int,
    val changeTimestamp: String?,
    val changeDescription: String?
) {
    constructor(
        productHistory: ProductHistoryResponse
    ) : this(
        productHistId = productHistory.id.toLong(),
        productId = productHistory.productId,
        oldPrice = productHistory.oldPrice.formatPriceAlternative(),
        newPrice = productHistory.newPrice.formatPriceAlternative(),
        oldQuantity = productHistory.oldQuantity,
        newQuantity = productHistory.newQuantity,
        changeTimestamp = productHistory.changeTimestamp.formatDateToString(),
        changeDescription = productHistory.changeDescription,
    )
}