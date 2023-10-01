package com.example.project.domain.satellite

import com.example.project.repository.satelliteService.PositionRepository
import com.example.project.repository.satelliteService.model.ListPosition
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PositionUseCase @Inject constructor(private val repository: PositionRepository) {

    fun execute(id: String): Flow<ListPosition> {
        return repository.getPositionById(id)
    }
}
