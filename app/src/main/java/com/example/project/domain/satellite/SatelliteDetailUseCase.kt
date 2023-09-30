package com.example.project.domain.satellite

import com.example.project.repository.satelliteService.SatelliteDetailRepository
import com.example.project.repository.satelliteService.SatelliteRepository
import com.example.project.repository.satelliteService.model.SatelliteDetailModel
import com.example.project.repository.satelliteService.model.SatelliteModel
import javax.inject.Inject

class SatelliteDetailUseCase @Inject constructor (private val repository: SatelliteDetailRepository) {

    fun execute(): List<SatelliteDetailModel>? {
        return repository.getMyDataFromAsset()
    }
}