package com.example.todolist

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.data.Task
import com.example.todolist.data.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.fragment_add_task.view.*

class AddTaskFragment : Fragment() {

    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)
        val animationBackground = AnimationUtils.loadAnimation(this.context, R.anim.background_animation)
        val animationEditField = AnimationUtils.loadAnimation(this.context, R.anim.edirfield_animation)
        view.animation_back.startAnimation(animationBackground)
        view.field_of_edits.startAnimation(animationEditField)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        view.add_button.setOnClickListener {
            putData()
        }

        return view
    }

    private fun putData() {
        val nameOfTask = input_name_of_task.text.toString()
        val description = input_description_view.text.toString()

        if (inputCheck(nameOfTask)) {
            val task = Task(0, nameOfTask, description, "Tomorrow")
            mTaskViewModel.addTask(task)
            Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addTaskFragment_to_toDoListFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out the name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(nameOfTask: String): Boolean {
        return !(TextUtils.isEmpty(nameOfTask))
    }
}