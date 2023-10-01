package com.example.project.ui.list

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.project.domain.satellite.SatelliteUseCase
import com.example.project.repository.satelliteService.model.SatelliteModel
import com.example.project.ui.adapter.SatelliteListAdapter
import com.example.project.util.CustomItemAnimator
import com.example.project.util.UiUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import java.util.Locale
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
@HiltViewModel
class ListActivityVM @Inject constructor(
    private val satelliteUseCase: SatelliteUseCase,
) :
    ViewModel() {

    private lateinit var satelliteListAdapter: SatelliteListAdapter
    private val satelliteList: ArrayList<SatelliteModel> by lazy { ArrayList() }
    private var tempSatelliteList: ArrayList<SatelliteModel> = ArrayList()


    private val searchDelayMillis: Long = 300
    private val handler = Handler(Looper.getMainLooper())


    suspend fun getList(activity: Activity, swipeRefreshLayout: SwipeRefreshLayout) {
        satelliteUseCase.execute().onStart {
            Log.i("TAG", "getList: onStart")
            swipeRefreshLayout.isRefreshing = true
        }.catch {
            swipeRefreshLayout.isRefreshing = false
            UiUtil.customAlertDialog(
                activity,
                it.message.toString()
            )
        }.collect {
            satelliteList.clear()
            satelliteList.addAll(it)
            tempSatelliteList = it as ArrayList<SatelliteModel>
            satelliteListAdapter.updateData(tempSatelliteList)
            swipeRefreshLayout.isRefreshing = false
        }

    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setupUI(view: View, activity: Activity) {

        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideSoftKeyboard(activity)
                false
            }
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView, activity)
            }
        }
    }

    fun search(editText: EditText, swipeRefreshLayout: SwipeRefreshLayout) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    val searchText = s.toString().trim().lowercase(Locale.ROOT)
                    searchItems(searchText, swipeRefreshLayout)
                }, searchDelayMillis)

            }
        })
    }

    fun setList(recyclerView: RecyclerView, activity: Activity) {
        activity.runOnUiThread {
            satelliteListAdapter = SatelliteListAdapter(tempSatelliteList, activity)
            recyclerView.layoutManager = LinearLayoutManager(activity);
            recyclerView.post {
                recyclerView.adapter = satelliteListAdapter
                recyclerView.itemAnimator = CustomItemAnimator()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchItems(query: String, swipeRefreshLayout: SwipeRefreshLayout) {

        swipeRefreshLayout.isRefreshing = true

        val filteredList = satelliteList.filter {
            it.name?.lowercase(Locale.ROOT)?.contains(query) ?: false
        }

        tempSatelliteList.clear()
        tempSatelliteList.addAll(filteredList)
        satelliteListAdapter.updateData(tempSatelliteList)
        swipeRefreshLayout.isRefreshing = false

    }
}