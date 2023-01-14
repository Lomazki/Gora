package com.pethabittracker.gora.data.mapper

import com.pethabittracker.gora.data.models.HabitEntity
import com.pethabittracker.gora.domain.models.Habit
import com.pethabittracker.gora.domain.models.HabitId

internal fun List<Habit>.toDomainModelsForEntity(): List<HabitEntity> = map {
    it.toDomain()
}

internal fun List<HabitEntity>.toDomainModels(): List<Habit> = map {
    it.toDomain()
}

internal fun HabitId.toDomain(): Int {
    return id
}

internal fun HabitEntity.toDomain(): Habit {
    return Habit(
        id = HabitId(id),
        name = name,
        urlImage = urlImage
    )
}

internal fun Habit.toDomain(): HabitEntity {
    return HabitEntity(
        id = id.toDomain(),
        name = name,
        urlImage = urlImage
    )
}