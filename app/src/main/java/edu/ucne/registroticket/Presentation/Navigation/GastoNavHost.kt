package edu.ucne.registroticket.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registroticket.Presentation.Gastos.GastoListScreen
import edu.ucne.registroticket.Presentation.Gastos.GastoScreen
import edu.ucne.registroticket.presentation.Navigation.Screen

@Composable
fun GastoNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.GastoList
    ) {
        composable <Screen.GastoList> {
            GastoListScreen (
                goToGasto = {
                    navHostController.navigate(Screen.Gasto(it))
                },
                createGasto = {
                    navHostController.navigate(Screen.Gasto(0))
                }
            )
        }

        composable<Screen.Gasto> {
            val args = it.toRoute<Screen.Gasto>()
            GastoScreen (
                GastoId = args.gastoId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}