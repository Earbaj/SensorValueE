package com.example.sensors.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensors.data.SensorValue
import com.example.sensors.data.SensorValueDao
import kotlinx.coroutines.launch



class SensorsViewModel(private val sensorDao: SensorValueDao) : ViewModel() {

    //Inserts the new Item into database.
    fun addNewItem(itemValue: Double) {
        val newItem = getNewItemEntry(itemValue)
        insertItem(newItem)
    }


    //Launching a new coroutine to insert an item in a non blocking way
    private fun insertItem(item: SensorValue) {
        viewModelScope.launch {
            sensorDao.insert(item)
        }
    }

    private fun getNewItemEntry(itemName: Double): SensorValue {
        return SensorValue(
            lightV = itemName
        )
    }

    //Retrive data from databasse
    fun getAllData(): List<SensorValue>{
        var listOfValues = listOf<SensorValue>()
        viewModelScope.launch {
            listOfValues = sensorDao.getAllItem()
        }
        return listOfValues
    }


}