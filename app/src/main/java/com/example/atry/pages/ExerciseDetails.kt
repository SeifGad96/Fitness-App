package com.example.atry.pages

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.example.atry.api.Exercise
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.atry.viewmodel.HomeViewModel


@Composable
fun ExerciseDetails( exerciseId: String, viewModel: HomeViewModel) {
    val exercise = remember { mutableStateOf<Exercise?>(null) }

    LaunchedEffect(exerciseId) {
        Log.d("HomeViewModel", "Fetching exercise with ID: $exerciseId")

        viewModel.fetchExerciseById(exerciseId){fetchedExercise ->
            exercise.value = fetchedExercise
        }
    }


    exercise.value?.let { exerciseData ->
        LazyColumn (modifier = Modifier.padding(16.dp)) {
            item { Text(text = exerciseData.name, style = MaterialTheme.typography.displaySmall)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Body Part: ${exerciseData.bodyPart}", fontWeight = FontWeight.Bold, color = Color.Red)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Target: ${exerciseData.target}",fontWeight = FontWeight.Bold, color = Color.Red)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Equipment: ${exerciseData.equipment}",fontWeight = FontWeight.Bold, color = Color.Red)

                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Secondary Muscles: ${exerciseData.secondaryMuscles.joinToString(", ")}",fontWeight = FontWeight.Bold, color = Color.Red)
                Spacer(modifier = Modifier.height(10.dp))
                AsyncImage(
                    model = exerciseData.gifUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                exerciseData.instructions.forEach { instruction ->
                    Text(text = instruction)
                }
            Spacer(modifier = Modifier.padding(bottom = 150.dp))
            }

        }

}}
