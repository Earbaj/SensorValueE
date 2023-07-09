package com.example.sensors.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SensorValue::class], version = 1, exportSchema = false)
abstract class SensorValueDatabase: RoomDatabase() {

    abstract fun sensorDao(): SensorValueDao

    companion object {
        @Volatile
        private var INSTANCE: SensorValueDatabase? = null
        fun getDatabase(context: Context): SensorValueDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SensorValueDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}