package com.example.todolist.presentation.taskList

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.todolist.R
import com.example.todolist.presentation.presenters.taskListPresenter.TaskListPresenter
import com.example.todolist.presentation.presenters.taskListPresenter.TaskListPresenterImpl
import com.example.todolist.util.NIGHT_MODE
import com.example.todolist.util.SAVED_STATE
import kotlinx.android.synthetic.main.fragment_to_do_list.*
import kotlinx.android.synthetic.main.fragment_to_do_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskListFragment : MvpAppCompatFragment(), TaskListView {

    @ProvidePresenter
    fun provideTaskListPresenter(): TaskListPresenter {
        return TaskListPresenterImpl(
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            .inflate(R.layout.fragment_to_do_list, container, false)
        initSwitchTheme(view)
        initRecyclerView()
        initSortButton(view)
        initFab(view)
        return view
    }

    override fun initRecyclerView() {
        GlobalScope.launch(Dispatchers.Main) {
            provideTaskListPresenter().initRecyclerView(list_of_task, findNavController(), requireContext())
        }
    }

    override fun initSortButton(v: View) {
        v.sorting_button.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                provideTaskListPresenter().initSortButton(findNavController(), sorting_button)
            }
        }
    }

    override fun initFab(v: View) {
        val animForFab = AnimationUtils.loadAnimation(
            this.context,
            R.anim.fab_animation
        )
        v.floating_action_bar.startAnimation(animForFab)
        v.floating_action_bar.setOnClickListener {
            findNavController()
                .navigate(R.id.action_toDoListFragment_to_addTaskFragment)
        }
    }

    override fun initSwitchTheme(v: View) {
        if (loadState()) {
            v.theme_switch.isChecked = true
        }
        v.theme_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveState(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveState(false)
            }
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
