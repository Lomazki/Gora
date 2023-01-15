package com.pethabittracker.gora.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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


//    fun onButtonAddHabit(habit: Habit) {
//        Log.d("Check", "ViewModel")
//
//        flow<Unit> {
//            repository.deleteHabits(habit)
//            Log.d("Check", "ViewModelDelete")
//
//        }.launchIn(viewModelScope)
//    }

    suspend fun deleteHabit(habit: Habit) = withContext(Dispatchers.IO) {
        repository.deleteHabits(habit)
    }

    suspend fun getAllHabits() = withContext(Dispatchers.IO) {
        repository.getAllHabits()
    }

    suspend fun insertHabit(habit: Habit) = withContext(Dispatchers.IO) {
        repository.insertHabits(habit)
    }

    suspend fun updateHabit(habit: Habit, priority: Int) = withContext(Dispatchers.IO) {
        repository.updateHabitPriority(habit, priority)
    }
}
