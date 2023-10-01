package com.example.project.repository.satelliteService

import android.content.Context
import com.example.project.repository.satelliteService.model.SatelliteModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SatelliteRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getMyDataFromAsset(): Flow<List<SatelliteModel>> {
        return flow {
            try {
                val inputStream = context.assets.open("satellites.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                val jsonString = String(buffer, Charsets.UTF_8)
                val data =
                    Gson().fromJson(jsonString, Array<SatelliteModel>::class.java).toList()
                emit(data)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(emptyList())
            }
        }.flowOn(Dispatchers.IO)
    }

}