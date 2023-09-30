package com.example.project.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.project.domain.satellite.SatelliteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG: String = "LoginActivityVM"

@HiltViewModel
class LoginActivityVM @Inject constructor(private val satelliteUseCase: SatelliteUseCase) : ViewModel() {

    fun test(){
        Log.i(TAG, "test: ${satelliteUseCase.execute()}")
    }

}