package com.example.project.repository.satelliteService

import com.example.project.network.ApiRepository
import com.example.project.repository.satelliteService.reqres.Coin
import com.example.project.repository.satelliteService.reqres.CoinDetail
import com.example.project.repository.satelliteService.reqres.PriceModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class SatelliteRepositoryImpl @Inject constructor(private val service: SatelliteService) : ApiRepository(),
    SatelliteRepository {

    override fun getCoinList(): Flow<Response<List<Coin>>> = sendRequest { service.getCoinList() }

    override fun getCoinById(id: String?): Flow<CoinDetail> =
        sendRequest { service.getCoinById(id) }

    override fun getPriceById(id: String?): Flow<Response<List<PriceModel>>> =
        sendRequest { service.getCoinPriceById(id) }

}