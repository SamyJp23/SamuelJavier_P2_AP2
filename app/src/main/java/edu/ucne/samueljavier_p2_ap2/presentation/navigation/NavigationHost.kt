package edu.ucne.samueljavier_p2_ap2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.samueljavier_p2_ap2.presentation.deposito.DepositoListScreen
import edu.ucne.samueljavier_p2_ap2.presentation.deposito.DepositoScreen
import edu.ucne.samueljavier_p2_ap2.presentation.home.Home

@Composable
fun AppNavHost(navHostController: NavHostController
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val scope = rememberCoroutineScope()
    NavHost(

        navController = navHostController,
        startDestination = Screen.Home
    ) {

        composable<Screen.Home>{
            Home(
                goToDeposito = {
                    navHostController.navigate(Screen.DepositoListScreen)
                }


            )
        }

        composable<Screen.DepositoScreen> {
            val depositoId = it.toRoute<Screen.DepositoScreen>().depositoId
            DepositoScreen(
                depositoId = depositoId,
                goBackToList = { navHostController.navigateUp() }

            )
        }

        composable<Screen.DepositoListScreen> {
            DepositoListScreen(
                createDeposito = { navHostController.navigate(Screen.DepositoScreen(0)) },
                goToMenu = { navHostController.navigateUp() },
                goToDeposito = { depositoId ->
                    navHostController.navigate(Screen.DepositoScreen(depositoId = depositoId))
                }
            )

        }
    }
}