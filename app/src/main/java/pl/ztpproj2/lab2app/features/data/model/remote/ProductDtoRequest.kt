package pl.ztpproj2.lab2app.features.data.model.remote

data class ProductDtoRequest (
    val name: String,
    val price: Double,
    val quantity: Int
)