package com.example.atry.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BodyBuildingViewModel:ViewModel() {
    // MutableLiveData to hold the currently selected category
    private val _selectedCategory = MutableLiveData("back")

    // Public LiveData to observe changes
    val selectedCategory: LiveData<String> get() = _selectedCategory

    // Method to change the selected category
    fun updateCategory(newCategory: String) {
        _selectedCategory.value = newCategory
    }
}