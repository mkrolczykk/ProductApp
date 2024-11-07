package pl.ztpproj2.lab2app.features.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import pl.ztpproj2.lab2app.features.data.model.remote.ProductDtoRequest
import pl.ztpproj2.lab2app.features.data.validation.NameSpecification
import pl.ztpproj2.lab2app.features.data.validation.PriceSpecification
import pl.ztpproj2.lab2app.features.data.validation.QuantitySpecification
import pl.ztpproj2.lab2app.features.domain.product.AddProductUseCase
import pl.ztpproj2.lab2app.features.domain.product.UpdateProductUseCase
import pl.ztpproj2.lab2app.features.presentation.model.ProductDisplayable

@KoinViewModel
class ProductEditOrAddViewModel(
    @Provided private val initialProduct: ProductDisplayable?,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
) : ViewModel() {
    private val nameSpec = NameSpecification()
    private val priceSpec = PriceSpecification()
    private val quantitySpec = QuantitySpecification()

    private val _name = mutableStateOf(initialProduct?.name ?: "")
    val name: State<String> = _name
    private val _nameError = mutableStateOf<String?>(null)
    val nameError: State<String?> = _nameError

    private val _price = mutableStateOf(initialProduct?.price?.toString() ?: "")
    val price: State<String> = _price
    private val _priceError = mutableStateOf<String?>(null)
    val priceError: State<String?> = _priceError

    private val _quantity = mutableStateOf(initialProduct?.quantity?.toString() ?: "")
    val quantity: State<String> = _quantity
    private val _quantityError = mutableStateOf<String?>(null)
    val quantityError: State<String?> = _quantityError

    fun onNameChange(newName: String) {
        _name.value = newName
        validateName()
    }

    fun onPriceChange(newPrice: String) {
        _price.value = newPrice
        validatePrice()
    }

    fun onQuantityChange(newQuantity: String) {
        _quantity.value = newQuantity
        validateQuantity()
    }

    private fun validateName() {
        _nameError.value = if (nameSpec.isSatisfiedBy(name.value)) null else nameSpec.errorMessage()
    }

    private fun validatePrice() {
        _priceError.value =
            if (priceSpec.isSatisfiedBy(price.value)) null else priceSpec.errorMessage()
    }

    private fun validateQuantity() {
        _quantityError.value =
            if (quantitySpec.isSatisfiedBy(quantity.value)) null else quantitySpec.errorMessage()
    }

    fun saveProduct(onSave: () -> Unit) {
        if (validateAll()) {
            viewModelScope.launch {
                if (initialProduct != null) {
                    updateProduct(
                        id = initialProduct.productId.toString(),
                        productDtoRequest = ProductDtoRequest(
                            name = name.value,
                            price = price.value.toDoubleOrNull() ?: initialProduct.price,
                            quantity = quantity.value.toIntOrNull() ?: initialProduct.quantity
                        ),
                        onSave = onSave
                    )
                } else {
                    addProduct(
                        productDtoRequest = ProductDtoRequest(
                            name = name.value,
                            price = price.value.toDoubleOrNull() ?: 0.0,
                            quantity = quantity.value.toIntOrNull() ?: 0,
                        ),
                        onSave = onSave
                    )
                }
            }
        }
    }

    private suspend fun addProduct(productDtoRequest: ProductDtoRequest, onSave: () -> Unit) =
        runCatching { addProductUseCase(productDtoRequest = productDtoRequest) }.onSuccess { onSave() }

    private suspend fun updateProduct(
        id: String,
        productDtoRequest: ProductDtoRequest,
        onSave: () -> Unit
    ) = runCatching {
        updateProductUseCase(
            id = id,
            productDtoRequest = productDtoRequest
        )
    }.onSuccess { onSave() }


    private fun validateAll(): Boolean {
        validateName()
        validatePrice()
        validateQuantity()
        return _nameError.value == null && _priceError.value == null && _quantityError.value == null
    }
}