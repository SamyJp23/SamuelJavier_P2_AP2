package edu.ucne.samueljavier_p2_ap2.data.remote

import edu.ucne.samueljavier_p2_ap2.data.remote.dto.DepositoDto
import javax.inject.Inject

class DataSource @Inject constructor(
private val depositoManagerApi: DepositoApiManager
){
    suspend fun getDepositos() = depositoManagerApi.getDepositos()

    suspend fun getDeposito(id: Int) = depositoManagerApi.getDeposito(id)

    suspend fun saveDeposito(depositoDto: DepositoDto) = depositoManagerApi.saveDeposito(depositoDto)

    suspend fun deleteDeposito(id: Int) = depositoManagerApi.deleteDeposito(id)

    suspend fun updateDeposito(id: Int, depositoDto: DepositoDto) = depositoManagerApi.actualizarDeposito(id, depositoDto)


}