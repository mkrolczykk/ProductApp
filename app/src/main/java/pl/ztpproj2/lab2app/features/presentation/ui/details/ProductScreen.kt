package pl.ztpproj2.lab2app.features.presentation.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import pl.ztpproj2.lab2app.features.presentation.model.ProductDisplayable
import pl.ztpproj2.lab2app.features.presentation.model.ProductHistoryDisplayable
import pl.ztpproj2.lab2app.features.presentation.model.formatPriceAlternative
import pl.ztpproj2.lab2app.features.presentation.model.getStatusType
import pl.ztpproj2.lab2app.features.presentation.viewmodel.ProductViewModel

@Composable
fun ProductScreen(
    navController: NavHostController,
    product: ProductDisplayable,
    viewModel: ProductViewModel = koinViewModel { parametersOf(product) }
) {
    val productState = viewModel.product.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = Icons.Default.Close.name
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Szczegóły Produktu o id ${product.productId}",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        when (productState) {
            ProductViewModel.ProductDetailsState.None -> Unit
            is ProductViewModel.ProductDetailsState.Success -> {
                ProductDetailsSection(
                    product = productState.product
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Historia Zmian Produktu",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ProductHistorySection(historyList = productState.history, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun ProductDetailsSection(product: ProductDisplayable) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nazwa: ${product.name}", fontSize = 18.sp)
            Text(text = "Cena: ${product.price.formatPriceAlternative()}", fontSize = 18.sp)
            Text(text = "Ilość: ${product.quantity}", fontSize = 18.sp)
            Text(
                text = "Status: ${product.status.getStatusType()}",
                fontSize = 18.sp
            )
            Text(text = "Usunięty: ${if (product.isDeleted) "Tak" else "Nie"}", fontSize = 18.sp)
            Text(text = "Utworzono: ${product.createdAt}", fontSize = 18.sp)
            Text(text = "Zaktualizowano: ${product.updatedAt ?: "Brak"}", fontSize = 18.sp)
        }
    }
}

@Composable
fun ProductHistorySection(
    historyList: List<ProductHistoryDisplayable>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(historyList.size) { index ->
            ProductHistoryItem(history = historyList[index])
        }
    }
}

@Composable
fun ProductHistoryItem(history: ProductHistoryDisplayable) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Zmiana: ${history.changeDescription ?: "Brak opisu"}", fontSize = 16.sp)
            Text(
                text = "Poprzednia cena: ${history.oldPrice}PLN,\n" +
                        "Nowa cena: ${history.newPrice} PLN",
                fontSize = 16.sp
            )
            Text(
                text = "Poprzednia ilość: ${history.oldQuantity},\n" +
                        "Nowa ilość: ${history.newQuantity}",
                fontSize = 16.sp
            )
            Text(text = "Data zmiany: ${history.changeTimestamp ?: "Brak"}", fontSize = 16.sp)
        }
    }
}
