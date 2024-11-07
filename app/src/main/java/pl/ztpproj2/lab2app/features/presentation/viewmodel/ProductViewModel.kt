package pl.ztpproj2.lab2app.features.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import pl.ztpproj2.lab2app.features.domain.product.GetProductHistoryUseCase
import pl.ztpproj2.lab2app.features.domain.product.GetProductUseCase
import pl.ztpproj2.lab2app.features.presentation.model.ProductDisplayable
import pl.ztpproj2.lab2app.features.presentation.model.ProductHistoryDisplayable

@KoinViewModel
class ProductViewModel(
    private val productDisplayable: ProductDisplayable,
    private val getProduct: GetProductUseCase,
    private val getProductHistoryUseCase: GetProductHistoryUseCase,
) : ViewModel() {
    private val _product = MutableStateFlow<ProductDetailsState>(ProductDetailsState.None)
    val product: StateFlow<ProductDetailsState> = _product

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                Pair(
                    getProduct(productDisplayable.productId.toString()),
                    getProductHistoryUseCase(productDisplayable.productId.toString())
                )
            }.onSuccess { productAndHistory ->
                _product.value = ProductDetailsState.Success(
                    product = productAndHistory.first ?: productDisplayable,
                    history = productAndHistory.second
                )
            }.onFailure {
                Log.e("TAG", "loadProducts: $it")
            }
        }
    }

    sealed interface ProductDetailsState {
        data object None : ProductDetailsState
        data class Success(
            val product: ProductDisplayable,
            val history: List<ProductHistoryDisplayable>
        ) : ProductDetailsState
    }

}