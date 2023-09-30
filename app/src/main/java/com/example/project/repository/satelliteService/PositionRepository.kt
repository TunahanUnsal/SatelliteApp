package com.example.project.repository.satelliteService


import android.content.Context
import com.example.project.repository.satelliteService.model.PositionModel
import com.example.project.repository.satelliteService.model.SatelliteDetailModel
import com.example.project.repository.satelliteService.model.SatelliteModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow


class PositionRepository(private val context: Context) {

    fun getMyDataFromAsset(): List<PositionModel>? {
        val json = try {
            val inputStream = context.assets.open("positions.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            Gson().fromJson(jsonString, Array<PositionModel>::class.java).toList()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return json
    }

}