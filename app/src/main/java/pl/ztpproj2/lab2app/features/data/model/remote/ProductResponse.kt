package pl.ztpproj2.lab2app.features.data.model.remote

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ProductResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("status") val status: String,
    @SerializedName("createdAt") val createdAt: Date,
    @SerializedName("updatedAt") val updatedAt: Date? = null
)
