package com.example.sensors.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



//Data class for sensors
@Entity(tableName = "sensorValue")
data class SensorValue(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "light")
    val lightV: Double
)
