package com.example.todolist

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.data.TaskViewModel
import kotlinx.android.synthetic.main.fragment_to_do_list.*
import kotlinx.android.synthetic.main.fragment_to_do_list.view.*

const val NIGHT_MODE = "NIGHT_MODE"
const val SAVED_STATE = "SAVED_STATE"

class TaskListFragment : Fragment() {

    private lateinit var mViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            .inflate(R.layout.fragment_to_do_list, container, false)
        initSwitchTheme(view)
        initRecyclerView(view)
        initFab(view)
        return view
    }

    private fun initSwitchTheme(view: View) {
        if (loadState()) {
            view.theme_switch.isChecked = true
        }
        view.theme_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveState(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveState(false)
            }
        }
    }

    private fun initRecyclerView(view: View) {
        mViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val adapter = TaskListAdapter { task ->
            mViewModel.deleteTask(task)
        }
        val recyclerView = view.list_of_task
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mViewModel.readAllData.observe(viewLifecycleOwner, Observer { task ->
            adapter.setData(task)
        })
    }

    private fun initFab(view: View) {
        val animForFab = AnimationUtils.loadAnimation(this.context, R.anim.fab_animation)
        view.floating_action_bar.startAnimation(animForFab)
        view.floating_action_bar.setOnClickListener {
            findNavController()
                .navigate(R.id.action_toDoListFragment_to_addTaskFragment)
        }
    }

    private fun saveState(state: Boolean) {
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences(SAVED_STATE, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(NIGHT_MODE, state)
        editor.apply()
    }

    private fun loadState(): Boolean {
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences(SAVED_STATE, MODE_PRIVATE)
        return sharedPreferences.getBoolean(NIGHT_MODE, false)
    }
}
