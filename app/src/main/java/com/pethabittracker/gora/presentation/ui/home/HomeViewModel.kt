package com.pethabittracker.gora.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.pethabittracker.gora.domain.models.Habit
import com.pethabittracker.gora.domain.models.HabitId

class HomeViewModel : ViewModel() {

    val listHabit = listOf(
        Habit(HabitId(0), "Непривычка 1", "url"),
        Habit(HabitId(0), "Назв непр-ки ", "url"),
        Habit(HabitId(0), "Назв непривычки 3", "url"),
        Habit(HabitId(0), "Назв ошибки 4", "url"),
        Habit(HabitId(0), "Назве непривычки ", "url"),
        Habit(HabitId(0), "Непр-ка 6", "url"),
        Habit(HabitId(0), "Непривычка 7", "url"),
        Habit(HabitId(0), "Назв привычки 8", "url"),
        Habit(HabitId(0), "Назва привычки ", "url"),
        Habit(HabitId(0), "Назв непривычки 0", "url"),
        Habit(HabitId(0), "Назв непр-ки 11", "url"),
    )
}
