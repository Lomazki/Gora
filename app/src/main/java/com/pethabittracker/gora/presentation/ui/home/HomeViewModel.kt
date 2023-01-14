package com.pethabittracker.gora.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pethabittracker.gora.domain.models.Habit
import com.pethabittracker.gora.domain.repositories.HabitRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HabitRepository,
    ) : ViewModel() {

    private val dataFlow = MutableStateFlow(emptyList<Habit>())

    init {
        viewModelScope.launch {
            dataFlow.value = repository.getAllHabits()
        }
    }

    val listHabitFlow = dataFlow
        .map {
            runCatching {
                repository.getAllHabits()
            }.fold(
                onSuccess = {
                    it
                },
                onFailure = {
                    emptyList()
                }
            )
        }
        .shareIn(
            viewModelScope,
            SharingStarted.Eagerly,
            replay = 1
        )

    fun onButtonAddHabit(habit: Habit) {
        Log.d("Check", "ViewModel")

        flow<Unit> {
            repository.deleteHabits(habit)
            Log.d("Check", "ViewModelDelete")

        }.launchIn(viewModelScope)
    }
}
