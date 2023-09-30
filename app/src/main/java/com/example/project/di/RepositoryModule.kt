package com.example.project.di

import android.content.Context
import com.example.project.domain.satellite.PositionUseCase
import com.example.project.domain.satellite.SatelliteDetailUseCase
import com.example.project.domain.satellite.SatelliteUseCase
import com.example.project.repository.satelliteService.PositionRepository
import com.example.project.repository.satelliteService.SatelliteDetailRepository
import com.example.project.repository.satelliteService.SatelliteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class RepositoryModule {

    @Provides
    @ApplicationContext
    fun provideSatelliteRepository(context: Context): SatelliteRepository {
        return SatelliteRepository(context)
    }

    @Provides
    fun provideSatelliteUseCase(repository: SatelliteRepository): SatelliteUseCase {
        return SatelliteUseCase(repository)
    }

    @Provides
    @ApplicationContext
    fun provideSatelliteDetailRepository(context: Context): SatelliteDetailRepository {
        return SatelliteDetailRepository(context)
    }

    @Provides
    @Singleton
    fun provideSatelliteDetailUseCase(repository: SatelliteDetailRepository): SatelliteDetailUseCase {
        return SatelliteDetailUseCase(repository)
    }

    @Provides
    @ApplicationContext
    fun providePositionRepository(context: Context): PositionRepository {
        return PositionRepository(context)
    }

    @Provides
    @Singleton
    fun providePositionUseCase(repository: PositionRepository): PositionUseCase {
        return PositionUseCase(repository)
    }
}