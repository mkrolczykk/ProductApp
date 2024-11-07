package pl.ztpproj2.lab2app.features.presentation.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import pl.ztpproj2.lab2app.features.data.model.remote.ProductResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Parcelize
data class ProductDisplayable(
    val productId: Long,
    val name: String,
    val price: Double,
    val quantity: Int,
    val status: StatusType,
    val isDeleted: Boolean,
    val createdAt: String,
    val updatedAt: String? = null,
) : Parcelable {
    constructor(
        product: ProductResponse
    ) : this(
        productId = product.id.toLong(),
        name = product.name,
        price = product.price,
        quantity = product.quantity,
        status = StatusType.AVAILABLE,
        isDeleted = false,
        createdAt = product.createdAt.formatDateToString(),
        updatedAt = product.updatedAt?.formatDateToString(),
    )
}

fun Date.formatDateToString(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(this)
}

fun String.stringToDate(): Date? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.parse(this)
}

fun Double.formatPriceAlternative(): String {
    return String.format(Locale("pl", "PL"), "%.2f zł", this)
}

enum class StatusType {
    AVAILABLE, NOT_AVAILABLE
}

fun StatusType.getStatusType() = when (this) {
    StatusType.AVAILABLE -> "Dostępny"
    StatusType.NOT_AVAILABLE -> "Niedostępny"
}

fun ProductDisplayable.toJsonObject(): String = Gson().toJson(this)

fun String.toProductDisplayable(): ProductDisplayable =
    Gson().fromJson(this, ProductDisplayable::class.java)