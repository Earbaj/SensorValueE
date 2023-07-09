package com.example.sensors.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sensors.data.SensorValueDao

class SensorsViewModelFactory(private val sensorDao: SensorValueDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SensorsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SensorsViewModel(sensorDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}