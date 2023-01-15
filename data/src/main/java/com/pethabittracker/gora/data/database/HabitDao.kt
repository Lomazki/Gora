package com.pethabittracker.gora.data.database

import android.provider.ContactsContract
import androidx.room.*
import com.pethabittracker.gora.data.models.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(habit: HabitEntity)

    @Query("SELECT * FROM HabitEntity WHERE id = :id")
    fun getById(id: Int): HabitEntity

    @Query("SELECT * FROM HabitEntity")
    fun getHabitEntityList(): List<HabitEntity>

    @Query("SELECT * FROM HabitEntity")
    fun getFlowHabitEntityList(): Flow<List<HabitEntity>>

    @Delete
    fun deleteHabit(habit: HabitEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(habit: HabitEntity)
}
