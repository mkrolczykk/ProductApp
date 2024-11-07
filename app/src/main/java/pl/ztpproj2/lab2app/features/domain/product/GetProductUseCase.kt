package pl.ztpproj2.lab2app.features.domain.product

import org.koin.core.annotation.Factory
import pl.ztpproj2.lab2app.features.domain.model.remote.ProductService
import pl.ztpproj2.lab2app.features.domain.repository.ProductRepository

@Factory
class GetProductUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(id: String) = productRepository.getProduct(id)
}