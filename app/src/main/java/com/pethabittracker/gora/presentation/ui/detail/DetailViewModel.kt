package com.pethabittracker.gora.presentation.ui.detail

import androidx.lifecycle.ViewModel
import com.pethabittracker.gora.domain.models.Habit
import com.pethabittracker.gora.domain.repositories.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DetailViewModel(private val repository: HabitRepository) : ViewModel() {


    suspend fun newHabit(name: String, url: String) = withContext(Dispatchers.IO) {
            repository.newHabit(name, url)
    }

    suspend fun insertHabit(habit: Habit) = withContext(Dispatchers.IO) {
            repository.insertHabits(habit)
    }
}
