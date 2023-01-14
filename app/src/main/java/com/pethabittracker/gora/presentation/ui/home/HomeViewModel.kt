package com.pethabittracker.gora.presentation.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pethabittracker.gora.domain.models.Habit
import com.pethabittracker.gora.domain.repositories.HabitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch


class HomeViewModel(private val repository: HabitRepository) : ViewModel() {

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
}
