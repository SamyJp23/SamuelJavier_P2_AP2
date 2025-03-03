package edu.ucne.samueljavier_p2_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.samueljavier_p2_ap2.data.local.dao.DepositoDao
import edu.ucne.samueljavier_p2_ap2.data.local.entity.DepositoEntity

@Database(
    entities = [DepositoEntity::class],
    version = 1,
    exportSchema = false
)

abstract class ParcialDb(): RoomDatabase(){
    abstract fun depositoDao(): DepositoDao
}