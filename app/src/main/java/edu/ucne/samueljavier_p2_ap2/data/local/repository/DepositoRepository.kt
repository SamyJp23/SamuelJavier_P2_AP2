package edu.ucne.samueljavier_p2_ap2.data.local.repository

import android.util.Log
import edu.ucne.samueljavier_p2_ap2.data.local.dao.DepositoDao
import edu.ucne.samueljavier_p2_ap2.data.local.entity.DepositoEntity
import edu.ucne.samueljavier_p2_ap2.data.remote.DataSource
import edu.ucne.samueljavier_p2_ap2.data.remote.dto.DepositoDto
import edu.ucne.samueljavier_p2_ap2.data.remote.dto.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class DepositoRepository @Inject constructor(
    private val dataSource: DataSource,
    private val depositoDao: DepositoDao
){
    fun getDepositos(): Flow<Resource<List<DepositoEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val depositoRemoto = dataSource.getDepositos()

            val listaDepositos = depositoRemoto.map { dto ->
                DepositoEntity(
                    idDeposito = dto.idDeposito,
                    fecha = dto.fecha,
                    idCuenta = dto.idCuenta,
                    concepto = dto.concepto,
                    monto = dto.monto
                )
            }
            depositoDao.save(listaDepositos)
            emit(Resource.Success(listaDepositos))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            emit(Resource.Error("Error de conexión $errorMessage"))
        } catch (e: Exception) {
            val depositosLocales = depositoDao.getAll().first()
            if (depositosLocales.isNotEmpty())
                emit(Resource.Success(depositosLocales))
            else
                emit(Resource.Error("Error de conexión: ${e.message}"))
        }
    }

    suspend fun update(id: Int, depositoDto: DepositoDto) = dataSource.updateDeposito(id, depositoDto)
    suspend fun find(id: Int): DepositoEntity? {
        val depositosDto = dataSource.getDepositos()
        return depositosDto
            .firstOrNull { it.idDeposito == id }
            ?.let { depositoDto ->
                DepositoEntity(
                    idDeposito = depositoDto.idDeposito,
                    fecha = depositoDto.fecha,
                    idCuenta = depositoDto.idCuenta,
                    concepto = depositoDto.concepto,
                    monto = depositoDto.monto
                )
            }
    }

    suspend fun save(depositoDto: DepositoDto) = dataSource.saveDeposito(depositoDto)

    suspend fun delete(id: Int) = dataSource.deleteDeposito(id)

}