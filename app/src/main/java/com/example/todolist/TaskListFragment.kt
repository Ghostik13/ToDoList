package com.example.todolist

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.data.TaskViewModel
import kotlinx.android.synthetic.main.fragment_to_do_list.*
import kotlinx.android.synthetic.main.fragment_to_do_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskListFragment : Fragment() {

    private lateinit var mViewModel: TaskViewModel
    private var flag = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            .inflate(R.layout.fragment_to_do_list, container, false)
        mViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
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
        val adapter = TaskListAdapter({ task ->
            mViewModel.deleteTask(task)
            refreshFragment()
        }, { task ->
            val action =
                TaskListFragmentDirections.actionTaskListFragmentToDetailTaskFragment(task)
            findNavController().navigate(action)
        }, { task ->
            GlobalScope.launch(Dispatchers.IO) {
                mViewModel.updateTask(task)
                refreshFragment()
            }
        })
        val recyclerView = view.list_of_task
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        GlobalScope.launch(Dispatchers.Main) {
            val listOfData = withContext(Dispatchers.IO) {
                mViewModel.readAllDataByDone()
            }
            adapter.setData(listOfData)
        }
        initSortButton(view, adapter)
    }

    private fun initSortButton(view: View, adapter: TaskListAdapter) {
        view.sorting_button.setOnClickListener {
            if (flag == 0) {
                GlobalScope.launch(Dispatchers.Main) {
                    val listOfData = withContext(Dispatchers.IO) {
                        mViewModel.readAllDataByDate()
                    }
                    adapter.setData(listOfData)
                }
                flag = 1
                sorting_button.setBackgroundResource(R.drawable.sort_button_pressed)
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    val listOfData = withContext(Dispatchers.IO) {
                        mViewModel.readAllDataByDone()
                    }
                    adapter.setData(listOfData)
                }
                flag = 0
                sorting_button.setBackgroundResource(R.drawable.sort_button_normal)
            }
        }
    }

    private fun initFab(view: View) {
        val animForFab = AnimationUtils.loadAnimation(this.context, R.anim.fab_animation)
        view.floating_action_bar.startAnimation(animForFab)
        view.floating_action_bar.setOnClickListener {
            findNavController()
                .navigate(R.id.action_toDoListFragment_to_addTaskFragment)
        }
    }

    private fun refreshFragment() {
        findNavController().navigate(R.id.action_toDoListFragment_to_addTaskFragment)
        findNavController().navigate(R.id.action_addTaskFragment_to_toDoListFragment)
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
