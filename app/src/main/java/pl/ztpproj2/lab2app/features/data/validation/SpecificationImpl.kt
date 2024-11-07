package pl.ztpproj2.lab2app.features.data.validation

import pl.ztpproj2.lab2app.features.domain.validation.ProductSpecification

class NameSpecification : ProductSpecification<String> {
    override fun isSatisfiedBy(value: String) = value.isNotBlank()
    override fun errorMessage() = "Nazwa nie może być pusta."
}

class PriceSpecification : ProductSpecification<String> {
    override fun isSatisfiedBy(value: String) = value.toFloatOrNull()?.let { it >= 0 } == true
    override fun errorMessage() = "Cena musi być liczbą dodatnią."
}

class QuantitySpecification : ProductSpecification<String> {
    override fun isSatisfiedBy(value: String) = value.toIntOrNull()?.let { it >= 0 } == true
    override fun errorMessage() = "Ilość musi być liczbą dodatnią."
}