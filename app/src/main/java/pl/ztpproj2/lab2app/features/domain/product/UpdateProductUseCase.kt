package pl.ztpproj2.lab2app.features.domain.product

import org.koin.core.annotation.Factory
import pl.ztpproj2.lab2app.features.data.cache.ProductsResponseCacheControl
import pl.ztpproj2.lab2app.features.data.model.remote.ProductDtoRequest
import pl.ztpproj2.lab2app.features.domain.model.remote.ProductService

@Factory
class UpdateProductUseCase(
    private val productService: ProductService,
    private val productsResponseCacheControl: ProductsResponseCacheControl,
) {
    suspend operator fun invoke(id: String, productDtoRequest: ProductDtoRequest) =
        productService.updateProduct(id = id, productDtoRequest = productDtoRequest)
            .also { productsResponseCacheControl.updateCacheControl() }
}