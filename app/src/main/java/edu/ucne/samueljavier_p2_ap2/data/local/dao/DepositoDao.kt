package edu.ucne.samueljavier_p2_ap2.data.local.dao

@Dao
interface DepositoDao {
    @Upsert()
    suspend fun save(entidades: List<DepositoEntity>)
    @Query(
        """
            SELECT *
            FROM Depositos 
            WHERE idDeposito == :id limit 1
        """
    )
    suspend fun find(id: Int): DepositoEntity?
    @Delete
    suspend fun delete(deposito: DepositoEntity)
    @Query("SELECT * FROM Depositos")
    fun getAll(): Flow<List<DepositoEntity>>
}