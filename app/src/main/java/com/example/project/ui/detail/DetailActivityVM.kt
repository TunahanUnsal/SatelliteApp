package com.example.project.ui.detail


import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.project.domain.satellite.PositionUseCase
import com.example.project.domain.satellite.SatelliteDetailUseCase
import com.example.project.repository.satelliteService.model.ListPosition
import com.example.project.repository.satelliteService.model.PositionModel
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
    private val detailUseCase: SatelliteDetailUseCase
) : ViewModel() {

    var positionList: ArrayList<ListPosition> = ArrayList()
    lateinit var satelliteDetailFlow: LiveData<List<SatelliteDetailModel>>
    lateinit var positionFlow: LiveData<PositionModel>


    fun getPosition(activity: Activity) {
        positionFlow = positionUseCase.execute()
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

    }

    fun getDetail(activity: Activity) {
        satelliteDetailFlow = detailUseCase.execute()
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

    }
}