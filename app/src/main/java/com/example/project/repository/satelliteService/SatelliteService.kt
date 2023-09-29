package com.example.project.repository.satelliteService

import com.example.project.repository.satelliteService.reqres.Coin
import com.example.project.repository.satelliteService.reqres.CoinDetail
import com.example.project.repository.satelliteService.reqres.PriceModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SatelliteService {

    @GET("v1/coins")
    suspend fun  getCoinList(): Response<List<Coin>>

    @GET("v1/coins/{coinId}")
    suspend fun getCoinById(@Path(value = "coinId") coinId : String?): CoinDetail

    @GET("v1/coins/{coinId}/ohlcv/latest")
    suspend fun getCoinPriceById(@Path(value = "coinId") coinId : String?): Response<List<PriceModel>>
}