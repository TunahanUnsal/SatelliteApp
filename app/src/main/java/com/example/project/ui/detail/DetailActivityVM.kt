package com.example.project.ui.detail

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


@HiltViewModel
class DetailActivityVM @Inject constructor(

) : ViewModel() {

    var loadingFlag: Boolean = false

    suspend fun coinDetailFun(
        activity: Activity,
        context: Context,
        id: String,
        swipeRefreshLayout: SwipeRefreshLayout
    ) {
        /*coinByIdUseCase.invoke(
            parameter = id
        ).onStart {
            Log.i("TAG", "coinDetailFun: onStart")
            activity.runOnUiThread {
                swipeRefreshLayout.isRefreshing = true
            }
        }.catch {
            Log.i("TAG", "coinDetailFun: catch $it")
            activity.runOnUiThread {
                swipeRefreshLayout.isRefreshing = false
            }
        }.collect {
            activity.runOnUiThread {
                swipeRefreshLayout.isRefreshing = false
            }
        }*/
    }
}