package com.example.sensors.data

import android.app.Application

class SensorApplication: Application() {
    val database: SensorValueDatabase by lazy { SensorValueDatabase.getDatabase(this) }
}