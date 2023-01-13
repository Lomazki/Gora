package com.pethabittracker.gora.data.database

import androidx.room.*
import com.pethabittracker.gora.data.models.HabitEntity

@Dao
internal interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(habit: HabitEntity)

    @Query("SELECT * FROM HabitEntity WHERE id = :id")
    suspend fun getById(id: Int): HabitEntity

    @Query("SELECT * FROM HabitEntity")
    suspend fun getHabitEntityList(): List<HabitEntity>

    @Delete
    suspend fun deleteHabit(habitEntity: HabitEntity)
}
