package com.pethabittracker.gora.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pethabittracker.gora.NavigationDirections
import com.pethabittracker.gora.R
import com.pethabittracker.gora.databinding.FragmentContentBinding

class ContentFragment : Fragment() {

    private var _binding: FragmentContentBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentContentBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nestedController =
            (childFragmentManager
                .findFragmentById(R.id.container_content) as NavHostFragment)
                .navController

        with(binding){
            bottomNavigation.setupWithNavController(nestedController)

            fab.setOnClickListener {
                findNavController().navigate(NavigationDirections.actionGlobalFab())
            }

            // отключаем кликабельность средней кнопки
            bottomNavigation.menu.getItem(1).isEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
