package com.example.atry.explore

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.atry.pages.ExerciseList
import com.example.atry.viewmodel.ExercisesViewModel

@Composable
fun CardioExercises(modifier: Modifier = Modifier,exerciseViewModel: ExercisesViewModel,navController: NavController){
    val exercises by exerciseViewModel.exercises.collectAsState(emptyList())
    val selectedCategory = "cardio"
    LaunchedEffect(selectedCategory) {
        exerciseViewModel.deleteAllExercises()
        Log.d("trace","recomposition")
        exerciseViewModel.fetchExercises(selectedCategory,limit=15,offset =0 )
    }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(8.dp))
    {
        Text(text = "Cardio",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
        )
        if(exercises.isEmpty()){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }else{
            ExerciseList(exercises,navController)
        }
    }
}

