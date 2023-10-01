package com.example.project.cache

import android.content.Context
import android.content.SharedPreferences
import com.example.project.repository.satelliteService.model.SatelliteDetailModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class DetailCacheManager @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "detail_cache",
        Context.MODE_PRIVATE
    )

    fun saveDetail(id: Int, detail: SatelliteDetailModel?) {
        val editor = sharedPreferences.edit()
        editor.putString("detail_$id", Gson().toJson(detail))
        editor.apply()
    }

    fun getDetail(id: Int): SatelliteDetailModel? {
        val json = sharedPreferences.getString("detail_$id", null)
        return if (json != null) {
            Gson().fromJson(json, SatelliteDetailModel::class.java)
        } else {
            null
        }
    }
}
