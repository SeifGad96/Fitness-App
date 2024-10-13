package com.example.atry.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.atry.viewmodel.ExercisesViewModel

@Composable
fun WorkoutCategoriesScreen(workoutCategoryName: String,modifier: Modifier,exercisesViewModel: ExercisesViewModel,navController:NavController){
    when(workoutCategoryName){
        "Yoga" -> YogaExercises(modifier)
        "Body Building" -> BodyBuilding(modifier,exercisesViewModel, navController)
        "Cardio" -> CardioExercises(modifier,exercisesViewModel,navController)
        "Stretching" -> StretchingExercises()
    }

}