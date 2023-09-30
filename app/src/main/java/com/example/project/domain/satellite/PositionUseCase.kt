package com.example.project.domain.satellite

import com.example.project.repository.satelliteService.PositionRepository
import com.example.project.repository.satelliteService.SatelliteRepository
import com.example.project.repository.satelliteService.model.PositionModel
import com.example.project.repository.satelliteService.model.SatelliteModel
import javax.inject.Inject

class PositionUseCase @Inject constructor (private val repository: PositionRepository) {

    fun execute(): List<PositionModel>? {
        return repository.getMyDataFromAsset()
    }
}