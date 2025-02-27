package edu.ucne.samueljavier_p2_ap2.data.local.repository

import android.util.Log
import edu.ucne.samueljavier_p2_ap2.data.remote.DataSource
import edu.ucne.samueljavier_p2_ap2.data.remote.dto.DepositoDto
import edu.ucne.samueljavier_p2_ap2.data.remote.dto.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class DepositoRepository @Inject constructor(
    private val dataSource: DataSource
){
    fun getDepositos(): Flow<Resource<List<DepositoDto>>> = flow {
        try{
            emit(Resource.Loading())
            val depositos = dataSource.getDepositos()
            emit(Resource.Success(depositos))
        }catch (e: HttpException){
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            Log.e("DepositoRepository", "HttpException: $errorMessage")
            emit(Resource.Error("Error de conexion $errorMessage"))
        }catch (e: Exception){
            Log.e("DepositoRepository", "Exception: ${e.message}")
            emit(Resource.Error("Error: ${e.message}"))

        }
    }

    suspend fun update(id: Int, depositoDto: DepositoDto) =
        dataSource.updateDeposito(id, depositoDto)

    suspend fun find(id: Int) = dataSource.getDeposito(id)

    suspend fun saveDeposito(depositoDto: DepositoDto) = dataSource.saveDeposito(depositoDto)

    suspend fun delete(id: Int) = dataSource.deleteDeposito(id)
}