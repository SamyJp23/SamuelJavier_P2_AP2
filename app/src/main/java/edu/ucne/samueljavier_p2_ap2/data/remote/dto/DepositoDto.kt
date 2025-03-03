package edu.ucne.samueljavier_p2_ap2.data.remote.dto

import java.util.Date


class DepositoDto (

    val idDeposito: Int,
    val fecha: String,
    val idCuenta: Int,
    val concepto: String,
    val monto: Double

)