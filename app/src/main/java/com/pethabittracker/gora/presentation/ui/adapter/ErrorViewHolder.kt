package com.pethabittracker.gora.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pethabittracker.gora.databinding.ItemErrorBinding

class ErrorViewHolder(
    binding: ItemErrorBinding,
    onRetryClicked: () -> Unit
) : ViewHolder(binding.root) {

    init {
        binding.button.setOnClickListener { onRetryClicked() }
    }
}