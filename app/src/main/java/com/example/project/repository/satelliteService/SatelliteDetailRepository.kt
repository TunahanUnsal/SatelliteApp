package com.example.project.repository.satelliteService

import android.content.Context
import com.example.project.repository.satelliteService.model.SatelliteDetailModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SatelliteDetailRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getSatelliteDetailById(id: Int): Flow<SatelliteDetailModel?> {
        return flow {
            try {
                val inputStream = context.assets.open("satellite_detail.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                val jsonString = String(buffer, Charsets.UTF_8)
                val data =
                    Gson().fromJson(jsonString, Array<SatelliteDetailModel>::class.java).toList()

                val detail = data.firstOrNull { it.id == id }

                emit(detail)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(null)
            }
        }.flowOn(Dispatchers.IO)
    }
}
