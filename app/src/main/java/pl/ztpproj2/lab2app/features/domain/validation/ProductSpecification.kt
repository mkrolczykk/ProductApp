package pl.ztpproj2.lab2app.features.domain.validation


interface ProductSpecification<T> {
    fun isSatisfiedBy(value: T): Boolean
    fun errorMessage(): String
}