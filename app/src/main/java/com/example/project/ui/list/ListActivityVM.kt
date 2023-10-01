package com.example.project.ui.list

import android.annotation.SuppressLint
import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.domain.satellite.SatelliteUseCase
import com.example.project.repository.satelliteService.model.SatelliteModel
import com.example.project.ui.adapter.SatelliteListAdapter
import com.example.project.util.CustomItemAnimator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
@HiltViewModel
class ListActivityVM @Inject constructor(
    private val satelliteUseCase: SatelliteUseCase,
) :
    ViewModel() {

    lateinit var satelliteListAdapter: SatelliteListAdapter
    lateinit var satelliteList: ArrayList<SatelliteModel>
    var tempSatelliteList: ArrayList<SatelliteModel> = ArrayList()


    fun getData(activity: Activity) {
        viewModelScope.launch {
            satelliteList = satelliteUseCase.execute() as ArrayList<SatelliteModel>
            tempSatelliteList = satelliteUseCase.execute() as ArrayList<SatelliteModel>
        }
        satelliteListAdapter = SatelliteListAdapter(tempSatelliteList, activity)
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

    fun search(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim().lowercase(Locale.ROOT)
                searchItems(searchText)

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
    private fun searchItems(query: String) {

        val filteredList = satelliteList.filter {
            it.name?.lowercase(Locale.ROOT)?.contains(query) ?: false
        }

        tempSatelliteList.clear()
        tempSatelliteList.addAll(filteredList)

        satelliteListAdapter.notifyDataSetChanged()

    }

}