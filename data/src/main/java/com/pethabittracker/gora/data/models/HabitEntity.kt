package com.pethabittracker.gora.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class HabitEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    @ColumnInfo(name = "url_image")
    val urlImage: String,
)
