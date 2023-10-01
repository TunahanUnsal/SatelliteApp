package com.example.project.domain.satellite

import com.example.project.repository.satelliteService.SatelliteDetailRepository
import com.example.project.repository.satelliteService.model.SatelliteDetailModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SatelliteDetailUseCase @Inject constructor(private val repository: SatelliteDetailRepository) {

    fun execute(): Flow<List<SatelliteDetailModel>> {
        return repository.getMyDataFromAsset()
    }
}