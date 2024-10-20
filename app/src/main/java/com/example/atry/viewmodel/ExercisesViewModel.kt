package com.example.atry.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atry.api.Exercise
import com.example.atry.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExercisesViewModel:ViewModel() {
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises = _exercises.asStateFlow()
    private val _exerciseDetails = MutableStateFlow<Exercise?>(null)
    val exerciseDetails = _exerciseDetails.asStateFlow()

    fun fetchExercises(bodyPart: String, limit: Int, offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = RetrofitInstance.api.getExercisesByBodyPart(bodyPart, limit, offset)
                Log.d("result","$result")
                _exercises.value = result
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
    fun fetchExerciseById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val exercise = RetrofitInstance.api.getExerciseById(id)
                _exerciseDetails.value = exercise
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun fetchAllExercises(limit: Int, offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val result = RetrofitInstance.api.getAllExercises(limit, offset)
                _exercises.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    fun deleteExercise() {
        viewModelScope.launch(Dispatchers.IO) {
            _exerciseDetails.value = null
        }
    }

    // Method to delete all exercises
    fun deleteAllExercises() {
        viewModelScope.launch {
            // Clear the list of exercises
            _exercises.value = emptyList()
        }
    }
}