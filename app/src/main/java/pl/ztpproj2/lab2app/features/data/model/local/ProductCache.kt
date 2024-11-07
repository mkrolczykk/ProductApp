package pl.ztpproj2.lab2app.features.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.ztpproj2.lab2app.features.data.model.remote.ProductResponse
import pl.ztpproj2.lab2app.features.presentation.model.ProductDisplayable
import pl.ztpproj2.lab2app.features.presentation.model.StatusType
import pl.ztpproj2.lab2app.features.presentation.model.formatDateToString
import pl.ztpproj2.lab2app.features.presentation.model.getStatusType

@Entity
data class ProductCache(
    @PrimaryKey
    val id: Long,
    val name: String,
    val price: Double,
    val quantity: Int,
    val status: String,
    val createdAt: String,
    val updatedAt: String?,
) {
    constructor(productDisplayable: ProductDisplayable) : this(
        id = productDisplayable.productId,
        name = productDisplayable.name,
        price = productDisplayable.price,
        quantity = productDisplayable.quantity,
        status = productDisplayable.status.getStatusType(),
        createdAt = productDisplayable.createdAt,
        updatedAt = productDisplayable.updatedAt,
    )

    fun toProductDisplayable() = ProductDisplayable(
        productId = id,
        name = name,
        price = price,
        quantity = quantity,
        status = StatusType.AVAILABLE,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = false,
    )
}
