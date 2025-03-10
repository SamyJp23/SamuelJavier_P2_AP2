package edu.ucne.samueljavier_p2_ap2.data.remote

import edu.ucne.samueljavier_p2_ap2.data.remote.dto.DepositoDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DepositoApiManager{
    @GET("api/Depositos")
    suspend fun getDepositos(): List<DepositoDto>

    @GET("api/Depositos/{id}")
    suspend fun getDeposito(@Path("id")id: Int): DepositoDto

    @POST("api/Depositos")
    suspend fun saveDeposito(@Body depositoDto: DepositoDto?): DepositoDto

    @PUT("api/Depositos/{id}")
    suspend fun actualizarDeposito(
        @Path("id") depositoId: Int,
        @Body deposito: DepositoDto
    ): DepositoDto

    @DELETE("api/Depositos/{id}")
    suspend fun deleteDeposito(@Path("id") id: Int): ResponseBody

}