package pl.ztpproj2.lab2app.features.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pl.ztpproj2.lab2app.features.presentation.model.ProductDisplayable
import pl.ztpproj2.lab2app.features.presentation.model.toJsonObject
import pl.ztpproj2.lab2app.features.presentation.model.toProductDisplayable
import pl.ztpproj2.lab2app.features.presentation.ui.details.ProductScreen
import pl.ztpproj2.lab2app.features.presentation.ui.edit.ProductEditOrAddScreen
import pl.ztpproj2.lab2app.features.presentation.ui.list.ProductsListScreen

@Composable
fun MainNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "ProductsListScreen"
    ) {
        composable("ProductsListScreen") { ProductsListScreen(navController) }

        composable(
            route = "ProductEditOrAddScreen/{product}",
            arguments = listOf(navArgument("product") { type = NavType.StringType })
        ) { backStackEntry ->
            val product = backStackEntry.arguments?.getString("product")?.takeIf { it.isNotBlank() }
                ?.toProductDisplayable()

            if (product != null) {
                ProductEditOrAddScreen(
                    product = product,
                    navController = navController
                )
            } else {
                ProductEditOrAddScreen(
                    product = null,
                    navController = navController
                )
            }
        }

        composable(
            route = "ProductScreen/{product}",
            arguments = listOf(navArgument("product") { type = NavType.StringType })
        ) { backStackEntry ->
            val product = backStackEntry.arguments?.getString("product")?.takeIf { it.isNotBlank() }
                ?.toProductDisplayable()
            product?.let {
                ProductScreen(
                    navController,
                    product = product
                )
            }
        }
    }
}

fun NavController.navigateToProductScreen(product: ProductDisplayable) =
    navigate("ProductScreen/${product.toJsonObject()}")

fun NavController.navigateToProductEditOrAddScreen(product: ProductDisplayable?) =
    navigate("ProductEditOrAddScreen/${product?.toJsonObject() ?: ""}")