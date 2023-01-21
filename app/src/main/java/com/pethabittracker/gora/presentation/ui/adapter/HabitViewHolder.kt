package com.pethabittracker.gora.presentation.ui.adapter

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pethabittracker.gora.R
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
                buttonSkip.visibility = View.GONE

                root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.pastel_green))

                val paramsTextView = tvNameHabit.layoutParams as ConstraintLayout.LayoutParams
                paramsTextView.bottomToTop = buttonDone.id

                val paramsButtonDone = buttonDone.layoutParams as ConstraintLayout.LayoutParams
                paramsButtonDone.apply {
                    startToEnd = imageView.id
                    width = 600     // TODO поменять единицы измерения на dp (примерно 260dp)
                }
//                buttonDone.paddingEnd = 80dp    // хз как его установить
                buttonDone.apply {
                    setBackgroundResource(R.drawable.border_button_done_cancel) // не работает
                    setBackgroundColor(R.drawable.border_button_done_cancel) // не работает
                    setText(R.string.done_cancel)
                    background.setTint(ContextCompat.getColor(context, R.color.periwinkle))
//                    backgroundTintList = ContextCompat.getColorStateList(context, R.color.transparent) // альтернатива строке выше
                }
            }

            buttonSkip.setOnClickListener {
                buttonDone.visibility = View.GONE
                Toast.makeText(context, "I have been lazy", Toast.LENGTH_SHORT).show()
//                root.background.alpha = 0 // прикольный эффект, но не работает вместе с setCardBackgroundColor
                root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.transparent_2))

                val paramsTextView = tvNameHabit.layoutParams as ConstraintLayout.LayoutParams
                paramsTextView.bottomToTop = buttonSkip.id

                val paramsButtonSkip = buttonSkip.layoutParams as ConstraintLayout.LayoutParams
                paramsButtonSkip.apply {
                    endToEnd = itemHabit.id
                    width = 600     // TODO поменять единицы измерения на dp (примерно 260dp)
                }
//                buttonSkip.setBackgroundResource(R.drawable.border_button_skip_cancel) // не работает
//                buttonSkip.setBackgroundColor(R.drawable.border_button_skip_cancel) // работает почему-то, но криво

                // Чтобы заменить картинку в drawableLeft
//                buttonSkip.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.x_skip, 0, 0, 0)
//                buttonDone.paddingEnd = 80dp    // хз как его установить

                buttonSkip.setText(R.string.skip_cancel)
            }

            tvNameHabit.text = habit.name
        }
    }
}
