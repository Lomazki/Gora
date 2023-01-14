package com.pethabittracker.gora.presentation.ui.adapter

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pethabittracker.gora.databinding.ItemHabitBinding
import com.pethabittracker.gora.domain.models.Habit

class HabitViewHolder(
    private val binding: ItemHabitBinding,
    private val context: Context,
    private val onButtonDoneClicked: (Habit) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(habit: Habit) {

        with(binding) {
            buttonDone.setOnClickListener {
                onButtonDoneClicked(habit)
            }
            buttonSkip.setOnClickListener {
                Toast.makeText(context, "I have been lazy", Toast.LENGTH_SHORT).show()
            }

            textView.text = habit.name
        }

    }
}
