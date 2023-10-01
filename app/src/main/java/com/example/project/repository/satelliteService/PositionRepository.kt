package com.example.project.repository.satelliteService


import android.content.Context
import com.example.project.repository.satelliteService.model.PositionModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class PositionRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getMyDataFromAsset(): Flow<PositionModel> {
        return flow {
            try {
                val inputStream = context.assets.open("positions.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                val jsonString = String(buffer, Charsets.UTF_8)
                val data =
                    Gson().fromJson(jsonString, PositionModel::class.java)
                emit(data)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(PositionModel())
            }
        }.flowOn(Dispatchers.IO)
    }

}