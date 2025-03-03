package edu.ucne.samueljavier_p2_ap2.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.samueljavier_p2_ap2.data.local.database.ParcialDb
import edu.ucne.samueljavier_p2_ap2.data.remote.DepositoApiManager
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            ParcialDb::class.java,
            "Parcial.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(depositoDb: ParcialDb) = depositoDb.depositoDao()


    const val BASE_URL = "https://sagapi-dev.azurewebsites.net/"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesDepositoApiManager(moshi: Moshi): DepositoApiManager {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(DepositoApiManager::class.java)
    }
}