package com.example.project.repository.satelliteService

import com.example.project.repository.satelliteService.reqres.Coin
import com.example.project.repository.satelliteService.reqres.CoinDetail
import com.example.project.repository.satelliteService.reqres.PriceModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SatelliteRepository {

    fun getCoinList(): Flow<Response<List<Coin>>>
    fun getCoinById(id : String?): Flow<CoinDetail>
    fun getPriceById(id : String?): Flow<Response<List<PriceModel>>>

}