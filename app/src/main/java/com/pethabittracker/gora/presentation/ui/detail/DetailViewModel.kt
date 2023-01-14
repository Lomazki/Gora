package com.pethabittracker.gora.presentation.ui.detail

import androidx.lifecycle.ViewModel
import com.pethabittracker.gora.domain.models.Habit
import com.pethabittracker.gora.domain.repositories.HabitRepository


class DetailViewModel(private val repository: HabitRepository) : ViewModel() {

    fun newHabit(name: String, url: String) =
        repository.newHabit(name, url)

    suspend fun insertHabit(habit: Habit) =
        repository.insertHabits(habit)
}
