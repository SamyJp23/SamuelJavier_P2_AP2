package edu.ucne.samueljavier_p2_ap2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Depositos")
data class DepositoEntity(
    @PrimaryKey
    val idDeposito: Int,
    val fecha: String,
    val idCuenta: Int,
    val concepto: String,
    val monto: Double
)