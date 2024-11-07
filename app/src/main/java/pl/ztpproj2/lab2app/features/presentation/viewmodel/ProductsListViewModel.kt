package pl.ztpproj2.lab2app.features.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import pl.ztpproj2.lab2app.features.domain.product.DeleteProductUseCase
import pl.ztpproj2.lab2app.features.domain.product.GetProductsUseCase
import pl.ztpproj2.lab2app.features.presentation.model.ProductDisplayable

@KoinViewModel
class ProductsListViewModel(
    private val getProducts: GetProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
) : ViewModel() {
    private val _products = MutableStateFlow<List<ProductDisplayable>>(emptyList())
    val products: StateFlow<List<ProductDisplayable>> = _products

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("TAG", "getProducts: ", )
            runCatching {
                getProducts()
            }.onSuccess {
                _products.value = it
            }
        }
    }

    fun deleteProduct(productId: Long) {
        viewModelScope.launch {
            _products.value = _products.value.filterNot { it.productId == productId }
            runCatching {
                deleteProductUseCase(id = productId.toString())
            }.onSuccess {
                loadProducts()
            }
        }
    }
}