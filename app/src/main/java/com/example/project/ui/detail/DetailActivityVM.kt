package com.example.project.ui.detail


import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.project.cache.DetailCacheManager
import com.example.project.domain.satellite.PositionUseCase
import com.example.project.domain.satellite.SatelliteDetailUseCase
import com.example.project.repository.satelliteService.model.ListPosition
import com.example.project.repository.satelliteService.model.SatelliteDetailModel
import com.example.project.util.UiUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


@HiltViewModel
class DetailActivityVM @Inject constructor(
    private val positionUseCase: PositionUseCase,
    private val detailUseCase: SatelliteDetailUseCase,
    private val cacheManager: DetailCacheManager
) : ViewModel() {

    private val _satelliteDetailFlow = MutableLiveData<SatelliteDetailModel?>()
    val satelliteDetailFlow: LiveData<SatelliteDetailModel?> get() = _satelliteDetailFlow
    lateinit var positionFlow: LiveData<ListPosition>


    fun getPosition(activity: Activity, id: String) {
        positionFlow = positionUseCase.execute(id)
            .onStart {
                Log.i("TAG", "getDetail: onStart")
            }
            .catch { throwable ->
                UiUtil.customAlertDialog(
                    activity,
                    throwable.message.toString()
                )
            }
            .onCompletion { }
            .asLiveData() as MutableLiveData<ListPosition>

    }

    fun getDetail(activity: Activity, id: Int) {
        val cachedDetail = cacheManager.getDetail(id)
        if (cachedDetail != null) {
            _satelliteDetailFlow.value = cachedDetail
        } else {
            detailUseCase.execute(id)
                .onStart {
                    Log.i("TAG", "getDetail: onStart")
                }
                .catch { throwable ->
                    UiUtil.customAlertDialog(
                        activity,
                        throwable.message.toString()
                    )
                }
                .onCompletion { }
                .asLiveData()
                .observeForever { detail ->
                    if (detail != null) {
                        cacheManager.saveDetail(id, detail)
                        _satelliteDetailFlow.value = detail
                    }
                }
        }
    }
}