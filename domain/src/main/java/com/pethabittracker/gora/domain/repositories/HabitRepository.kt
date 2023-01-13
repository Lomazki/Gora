package com.pethabittracker.gora.domain.repositories

import com.pethabittracker.gora.domain.models.Habit

interface HabitRepository {

    suspend fun getHabits(): Habit

    suspend fun insertHabits(habit: Habit)

    suspend fun deleteHabits(habit: Habit)

}
