package com.koniukhov.brushbliss.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koniukhov.brushbliss.data.UserSettingsManager
import com.koniukhov.brushbliss.di.DispatcherIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSettingsViewModel @Inject constructor(private val userSettingsManager: UserSettingsManager) : ViewModel() {

    val userSettingsFlow = userSettingsManager.userSettingsFlow
    @DispatcherIO
    @Inject lateinit var dispatcherIO: CoroutineDispatcher

    fun updateStrokeWidth(newStrokeWidth: Float){
        viewModelScope.launch(dispatcherIO) {
            userSettingsManager.updateStrokeWidth(newStrokeWidth)
        }
    }

    fun updateAlpha(newAlpha: Int){
        viewModelScope.launch(dispatcherIO) {
            userSettingsManager.updateAlpha(newAlpha)
        }
    }
}