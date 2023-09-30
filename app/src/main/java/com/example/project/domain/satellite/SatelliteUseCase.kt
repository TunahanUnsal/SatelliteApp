package com.example.project.domain.satellite

import com.example.project.repository.satelliteService.SatelliteRepository
import com.example.project.repository.satelliteService.model.SatelliteModel
import javax.inject.Inject

class SatelliteUseCase @Inject constructor( private val repository: SatelliteRepository) {

    fun execute(): List<SatelliteModel>? {
        return repository.getMyDataFromAsset()
    }
}