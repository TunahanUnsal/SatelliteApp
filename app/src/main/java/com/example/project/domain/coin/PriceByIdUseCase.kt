package com.example.project.domain.coin

import com.example.project.network.UseCase
import com.example.project.repository.satelliteService.SatelliteRepository
import com.example.project.repository.satelliteService.reqres.PriceModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class PriceByIdUseCase @Inject constructor(private val repository: SatelliteRepository) :
    UseCase<String, Response<List<PriceModel>>>() {

    override fun execute(parameter: String?): Flow<Response<List<PriceModel>>> {
        return repository.getPriceById(parameter)
    }

}