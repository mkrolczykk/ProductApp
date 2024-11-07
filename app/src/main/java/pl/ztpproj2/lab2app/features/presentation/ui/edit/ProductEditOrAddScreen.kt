package pl.ztpproj2.lab2app.features.presentation.ui.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import pl.ztpproj2.lab2app.features.presentation.model.ProductDisplayable
import pl.ztpproj2.lab2app.features.presentation.viewmodel.ProductEditOrAddViewModel

@Composable
fun ProductEditOrAddScreen(
    product: ProductDisplayable? = null,
    navController: NavHostController,
    viewModel: ProductEditOrAddViewModel = koinViewModel { parametersOf(product) }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
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

        OutlinedTextField(
            value = viewModel.name.value,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Nazwa") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.nameError.value != null
        )
        if (viewModel.nameError.value != null) {
            Text(
                text = viewModel.nameError.value!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        OutlinedTextField(
            value = viewModel.price.value,
            onValueChange = { viewModel.onPriceChange(it) },
            label = { Text("Cena") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = viewModel.priceError.value != null
        )
        if (viewModel.priceError.value != null) {
            Text(
                text = viewModel.priceError.value!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = viewModel.quantity.value,
            onValueChange = { viewModel.onQuantityChange(it) },
            label = { Text("Ilość") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = viewModel.quantityError.value != null
        )
        if (viewModel.quantityError.value != null) {
            Text(
                text = viewModel.quantityError.value!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Button(
            onClick = {
                viewModel.saveProduct(onSave = { navController.popBackStack() })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (product != null) "Edytuj" else "Dodaj")
        }
    }
}