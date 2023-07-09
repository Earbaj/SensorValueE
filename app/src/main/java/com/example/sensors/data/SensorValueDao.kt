package com.example.sensors.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface SensorValueDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: SensorValue)

    @Update
    suspend fun update(item: SensorValue)

    @Delete
    suspend fun delete(item: SensorValue)

    @Query("DELETE FROM SensorValue")
    suspend fun deleteAllData()

    @Query("SELECT * from SensorValue WHERE id = :id")
    fun getItem(id: Int): Flow<SensorValue>

    @Query("SELECT * from SensorValue")
    suspend fun getAllItem(): List<SensorValue>
}