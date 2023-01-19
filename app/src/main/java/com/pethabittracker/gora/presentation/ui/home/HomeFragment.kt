package com.pethabittracker.gora.presentation.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.pethabittracker.gora.databinding.FragmentHomeBinding
import com.pethabittracker.gora.domain.models.Habit
import com.pethabittracker.gora.presentation.models.Lce
import com.pethabittracker.gora.presentation.ui.adapter.HabitAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModel<HomeViewModel>()
    private val adapter by lazy {
        HabitAdapter(
            context = requireContext(),
            onButtonDoneClicked = { viewModel.skipDown(it) },
            onRetryClicked = { viewModel.onRetry() }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentHomeBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            root.setBackgroundColor(Color.GRAY)
            recyclerView.adapter = adapter

            // Decorator
            recyclerView.addItemDecoration(
                MaterialDividerItemDecoration(
                    requireContext(),
                    MaterialDividerItemDecoration.VERTICAL
                )
            )
        }

        updateList()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition
                val habit: Habit =
                    requireNotNull((adapter.currentList[position] as? Lce.Content)?.data)   // не уверен в этой строчке
                lifecycleScope.launch {
                    viewModel.deleteHabit(habit)
                }
                // adapter.notifyItemRemoved(position)
                //  updateList()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateList() {
        Log.d("LCE", "UpdateList")

        viewModel.getLceFlow()
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { lceListHabit ->
                Log.d("LCE", "onEach")
                lceListHabit as Lce.Content
                adapter.submitList(lceListHabit.data.map { Lce.Content(it) })
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)


//        viewModel.currentLceFlow
//            .onEach {
//                Log.d("LCE", "onEach")
//            }
//            .launchIn(viewLifecycleOwner.lifecycleScope)

//        viewModel.currentLceFlow
//            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
//            .onEach { lceListHabit -> // сюда почему-то не заходит
//                Log.d("LCE", "onEach")  // даже эта строка не отрабатыват
//                when(lceListHabit) {
//                    is Lce.Content -> {
//                        Log.d("LCE", "Content")
//                        adapter.submitList(lceListHabit.data.map { Lce.Content(it) })
//                    }
//                    is Lce.Error -> {  }
//                    Lce.Loading -> {  }
//                }
//            }
//            .launchIn(viewLifecycleOwner.lifecycleScope)


        //------------------ with Coroutine -------------------------------------------------------
//        viewModel.getAllHabit()
//            .onEach {
//                adapter.submitList(Lce.Content(it))
//            }
//            .launchIn(lifecycleScope)

        //------------------ with LiveData -------------------------------------------------------
//        viewModel.allHabit.observe(this.viewLifecycleOwner) { items ->
//            items.let { list ->
//                adapter.submitList(list)
//            }
//        }
    }
}
