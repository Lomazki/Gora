package com.pethabittracker.gora.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pethabittracker.gora.domain.models.Habit
import com.pethabittracker.gora.domain.repositories.HabitRepository
import com.pethabittracker.gora.presentation.models.Lce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repository: HabitRepository,
) : ViewModel() {

    private val _dataFlow = MutableStateFlow(emptyList<Habit>())
    private val dataFlow: Flow<List<Habit>> = _dataFlow.asStateFlow()

//    private val lceFlow = MutableSharedFlow<Lce<Habit>>(
//        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
//    )

    private val lceFlow = MutableStateFlow(emptyList<Habit>())

//    init {
//        viewModelScope.launch {
//            lceFlow.value = repository.getFlowAllHabits().first()
//        }
//    }

    fun getLceFlow(): Flow<Lce<List<Habit>>> {
        Log.d("LCE", "getLceFlow")
        val result: Flow<Lce<List<Habit>>> =
            lceFlow
                .map {
                    Log.d("LCE", ".map to repository")
                    val a = repository.getFlowAllHabits().first() /* иногда на этой строке пишет
                     "Skipped 55 frames!  The application may be doing too much work on its main thread." */
                    Log.d("LCE", ".map after repository")
                    Lce.Content(a)
                }
//            .map {
//                Log.d("LCE", "currentLceFlow")
//                runCatching {
//                    repository.getFlowAllHabits()
//                }
//                    .fold(
//                        onSuccess = { it },
//                        onFailure = { emptyFlow() }
//                    )
//            }.map {
//                val listHabit: List<Habit> = it.single()
//                Lce.Content(listHabit)
//            }
                .shareIn(
                    viewModelScope,
                    SharingStarted.Eagerly,
                    1
                )
        return result
        /* Приложение запускается, но не адекватно реагирует при нажатии на кнопки "Сделано" или "Пропустить" */
    }

    //-------------------------------------------------------------------------------------

    val currentLceFlow: Flow<Lce<List<Habit>>> =
        lceFlow
            .map {
                // сюда даже не заходит
                Log.d("LCE", "currentLceFlow")
                runCatching {
                    repository.getFlowAllHabits()
                }
                    .fold(
                        onSuccess = { it },
                        onFailure = { emptyFlow() }
                    )
            }.map {
                val listHabit: List<Habit> = it.single()
                Lce.Content(listHabit)
            }.shareIn(
                viewModelScope,
                SharingStarted.Eagerly,
                1
            )

    // -------------------------------------------------------------------------------------
//    val currentLceFlow: Flow<Lce<List<Habit>>> = flow {
//        val lceState: Flow<Lce<List<Habit>>> =
//            runCatching {
//                repository.getFlowAllHabits()
//            }.fold(
//                onSuccess = { flowListHabit ->
//                    var content : Lce.Content<List<Habit>> = Lce.Content(emptyList<Habit>())
//                    flowListHabit.map { content = Lce.Content(it) }
//                    flow { content }
//                },
//                onFailure = { flow { Lce.Error(it) } }
//            )
//---------------------------------------------------------------------------------------------
//            runCatching {
//                repository.getFlowAllHabits()
//            }.map {
//                it.single()
//            }.fold(
//                onSuccess = {
//                    Lce.Content(it)
//                },
//                onFailure = { Lce.Error(it) }
//            )
//    val lceListHabit: Lce<List<Habit>> = Lce.Content(emptyList())
//    emit(lceListHabit)
//}.stateIn(
//scope = viewModelScope,
//started = SharingStarted.Eagerly,
//initialValue = Lce.Loading
//)


    //------------------ with Coroutine -------------------------------------------------------
    fun getAllHabit(): Flow<List<Habit>> {

        return dataFlow    // работает, но что-то здесь не то
            .runCatching {
                repository.getFlowAllHabits()
            }
            .fold(
                onSuccess = { flowListHabit -> flowListHabit.map { it } },
                onFailure = { emptyFlow() }
            )
    }

//----------------- with LiveData -------------------------------------------------------------

    val allHabit: LiveData<List<Habit>> = repository.getFlowAllHabits().asLiveData()

    fun skipDown(habit: Habit) {
        flow<Unit> {
            updateHabit(habit, 2)
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

    fun onRetry() {
        TODO("Not yet implemented")
    }
}
