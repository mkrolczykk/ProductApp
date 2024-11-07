package pl.ztpproj2.lab2app.features.data.model.remote

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ProductHistoryResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("productId") val productId: Int,
    @SerializedName("oldPrice") val oldPrice: Double,
    @SerializedName("newPrice") val newPrice: Double,
    @SerializedName("oldQuantity") val oldQuantity: Int,
    @SerializedName("newQuantity") val newQuantity: Int,
    @SerializedName("changeTimestamp") val changeTimestamp: Date,
    @SerializedName("changeDescription") val changeDescription: String
)