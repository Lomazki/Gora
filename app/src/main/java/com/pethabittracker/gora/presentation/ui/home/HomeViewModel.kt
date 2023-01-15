package com.pethabittracker.gora.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pethabittracker.gora.domain.models.Habit
import com.pethabittracker.gora.domain.repositories.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repository: HabitRepository,
) : ViewModel() {

    private val dataFlow = flowOf<List<Habit>>()

    val listHabitFlow = dataFlow
        .onStart {
            runCatching {
                repository.getAllHabits()
            }
                .fold(
                    onSuccess = {
                        emit(it)
                    },
                    onFailure = {
                        emit(emptyList())
                    }
                )
        }

    val allHabit: LiveData<List<Habit>> = repository.getFlowAllHabits().asLiveData()


//    val listHabitFlow: Flow<List<Habit>> = flow {
//        val data = repository.getAllHabits()
//        emit(data)
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.Eagerly,
//        initialValue = emptyList()
//    )


//    private val dataFlow = MutableStateFlow(emptyList<Habit>())
//
//    init {
//        viewModelScope.launch {
//            dataFlow.value = repository.getAllHabits()
//        }
//    }
//
//    val listHabitFlow = dataFlow
//        .map {
//            runCatching {
//                repository.getAllHabits()
//            }.fold(
//                onSuccess = {
//                    it
//                },
//                onFailure = {
//                    emptyList()
//                }
//            )
//        }
//        .shareIn(
//            viewModelScope,
//            SharingStarted.Eagerly,
//            replay = 1
//        )


    fun skipDown(habit: Habit) {
        flow<Unit> {
            updateHabit(habit,2)
        }.launchIn(viewModelScope)
    }

    suspend fun deleteHabit(habit: Habit) = withContext(Dispatchers.IO) {
        repository.deleteHabits(habit)
    }

    private suspend fun updateHabit(habit: Habit, priority: Int) = withContext(Dispatchers.IO) {
       runCatching {
            repository.updateHabitPriority(habit.id, habit.name, habit.urlImage, priority)
       }
    }
}
