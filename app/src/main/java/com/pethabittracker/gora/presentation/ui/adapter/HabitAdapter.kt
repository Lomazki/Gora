package com.pethabittracker.gora.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pethabittracker.gora.databinding.ItemErrorBinding
import com.pethabittracker.gora.databinding.ItemHabitBinding
import com.pethabittracker.gora.databinding.ItemLoadingBinding
import com.pethabittracker.gora.domain.models.Habit
import com.pethabittracker.gora.presentation.models.Lce

class HabitAdapter(
    private val context: Context,
    private val onButtonDoneClicked: (Habit) -> Unit,
    private val onRetryClicked: () -> Unit
) : ListAdapter<Lce<Habit>, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Lce.Content -> TYPE_CONTENT
            Lce.Loading -> TYPE_LOADING
            is Lce.Error -> TYPE_ERROR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CONTENT -> HabitViewHolder(
                ItemHabitBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                ),
                context = context,
                onButtonDoneClicked = onButtonDoneClicked
            )
            TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            TYPE_ERROR -> {
                ErrorViewHolder(
                    binding = ItemErrorBinding.inflate(layoutInflater, parent, false),
                    onRetryClicked = onRetryClicked
                )
            }
            else -> error("Unsupported viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val pokemon = getItem(position)) {
            is Lce.Content -> {
                checkNotNull(holder as HabitViewHolder) { "Incorrect ViewHolder $pokemon" }
                holder.bind(pokemon.data)
            }
            Lce.Loading -> {}
            is Lce.Error -> {
                Toast.makeText(layoutInflater.context, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val TYPE_CONTENT = 1
        private const val TYPE_LOADING = 2
        private const val TYPE_ERROR = 3

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Lce<Habit>>() {
            override fun areItemsTheSame(oldItem: Lce<Habit>, newItem: Lce<Habit>): Boolean {
                val oldHabit = oldItem as? Lce.Content
                val newHabit = newItem as? Lce.Content

                return oldHabit?.data?.id == newHabit?.data?.id
            }

            override fun areContentsTheSame(oldItem: Lce<Habit>, newItem: Lce<Habit>): Boolean {
                val oldHabit = oldItem as? Lce.Content
                val newHabit = newItem as? Lce.Content

                return oldHabit?.data?.name == newHabit?.data?.name &&
                        oldHabit?.data?.urlImage == newHabit?.data?.urlImage
            }
        }
    }
}
