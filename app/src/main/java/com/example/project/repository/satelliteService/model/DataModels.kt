package com.example.project.repository.satelliteService.model

import com.google.gson.annotations.SerializedName


data class PositionModel(
    @SerializedName("list") var list: ArrayList<ListPosition> = arrayListOf()
)

data class Positions(
    @SerializedName("posX") var posX: Double? = null,
    @SerializedName("posY") var posY: Double? = null
)

data class ListPosition(
    @SerializedName("id") var id: String? = null,
    @SerializedName("positions") var positions: ArrayList<Positions> = arrayListOf()
)

data class SatelliteDetailModel(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("cost_per_launch") var costPerLaunch: Int? = null,
    @SerializedName("first_flight") var firstFlight: String? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("mass") var mass: Int? = null
)

data class SatelliteModel(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("active") var active: Boolean? = null,
    @SerializedName("name") var name: String? = null

)