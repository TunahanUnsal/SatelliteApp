package com.example.project.di

import com.example.project.repository.satelliteService.SatelliteRepository
import com.example.project.repository.satelliteService.SatelliteRepositoryImpl
import com.example.project.repository.satelliteService.SatelliteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SatelliteModule {

    @Singleton
    @Provides
    fun provideCoinService(retrofit: Retrofit): SatelliteService =
        retrofit.create(SatelliteService::class.java)

    @Singleton
    @Provides
    fun provideCoinRepository(service: SatelliteService): SatelliteRepository =
        SatelliteRepositoryImpl(service)

}