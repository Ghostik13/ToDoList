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
import androidx.navigation.fragment.navArgs
import com.example.todolist.data.Task
import com.example.todolist.data.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add_task.view.*
import kotlinx.android.synthetic.main.fragment_detail_task.*
import kotlinx.android.synthetic.main.fragment_detail_task.view.*
import kotlinx.android.synthetic.main.fragment_detail_task.view.animation_back

class DetailTaskFragment : Fragment() {

    private val args by navArgs<DetailTaskFragmentArgs>()

    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_task, container, false)
        view.update_input_name_of_task.setText(args.currentTask.name)
        view.update_description_view.setText(args.currentTask.description)
        val animationBackground = AnimationUtils.loadAnimation(this.context, R.anim.background_animation)
        view.animation_back.startAnimation(animationBackground)
        view.update_add_button.setOnClickListener {
            updateTask(view)
        }
        return view
    }

    private fun updateTask(view: View) {
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val nameOfTask = update_input_name_of_task.text.toString()
        val description = update_description_view.text.toString()
        if (inputCheck(nameOfTask)) {
            val updatedTask = Task(args.currentTask.id, nameOfTask, description, "Today")
            mTaskViewModel.updateTask(updatedTask)
            Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detailTaskFragment_to_task_list_fragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out the name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(nameOfTask: String): Boolean {
        return !(TextUtils.isEmpty(nameOfTask))
    }
}


