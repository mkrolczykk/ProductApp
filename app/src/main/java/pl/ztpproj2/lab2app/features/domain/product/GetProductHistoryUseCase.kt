package pl.ztpproj2.lab2app.features.domain.product

import org.koin.core.annotation.Factory
import pl.ztpproj2.lab2app.features.domain.repository.ProductHistoryRepository

@Factory
class GetProductHistoryUseCase(private val productHistoryRepository: ProductHistoryRepository) {
    suspend operator fun invoke(id: String) = productHistoryRepository.getProductHistory(id)
}