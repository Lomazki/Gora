package com.pethabittracker.gora.data.repositories

import com.pethabittracker.gora.domain.models.Habit
import com.pethabittracker.gora.domain.repositories.HabitRepository

internal class HabitRepositoryImpl : HabitRepository {

    override suspend fun getHabits(): Habit {
        TODO("Not yet implemented")
    }

    override suspend fun insertHabits(habit: Habit) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHabits(habit: Habit) {
        TODO("Not yet implemented")
    }
}