package edu.ucne.samueljavier_p2_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object DepositoListScreen : Screen()

    @Serializable
    data class DepositoScreen(val depositoId: Int) : Screen()

    @Serializable
    data object Home : Screen()
}