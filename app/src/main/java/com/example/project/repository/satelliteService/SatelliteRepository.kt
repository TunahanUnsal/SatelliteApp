package com.example.project.repository.satelliteService

import android.content.Context
import com.example.project.repository.satelliteService.model.SatelliteModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SatelliteRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getMyDataFromAsset(): List<SatelliteModel>? {
        val json = try {
            val inputStream = context.assets.open("satellites.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            Gson().fromJson(jsonString, Array<SatelliteModel>::class.java).toList()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return json
    }

}