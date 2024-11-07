package pl.ztpproj2.lab2app.features.presentation.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import pl.ztpproj2.lab2app.features.presentation.model.ProductDisplayable
import pl.ztpproj2.lab2app.features.presentation.navigation.navigateToProductEditOrAddScreen
import pl.ztpproj2.lab2app.features.presentation.navigation.navigateToProductScreen
import pl.ztpproj2.lab2app.features.presentation.viewmodel.ProductsListViewModel

@Composable
fun ProductsListScreen(
    navController: NavHostController,
    viewModel: ProductsListViewModel = koinViewModel()
) {
    val products by viewModel.products.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lista Produktów",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products.size) { index ->
                ProductItem(
                    product = products[index],
                    onRemove = { viewModel.deleteProduct(productId = products[index].productId) },
                    onShowDetails = {
                        navController.navigateToProductScreen(product = products[index])
                    },
                    onEdit = {
                        navController.navigateToProductEditOrAddScreen(product = products[index])
                    }
                )
            }
        }
        Button(
            onClick = { navController.navigateToProductEditOrAddScreen(product = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Dodaj produkt")
        }
    }
}

@Composable
fun ProductItem(
    product: ProductDisplayable,
    onRemove: () -> Unit,
    onShowDetails: () -> Unit,
    onEdit: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { onEdit() }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = Icons.Default.Edit.name,
                    modifier = Modifier.size(20.dp)
                )
            }
            IconButton(onClick = { onShowDetails() }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = Icons.Default.Info.name,
                    modifier = Modifier.size(20.dp)
                )
            }
            IconButton(onClick = { onRemove() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = Icons.Default.Delete.name,
                    tint = Color(0xFFF44336),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}