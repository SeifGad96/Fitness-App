package com.example.atry.pages


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.atry.api.Exercise
import com.example.atry.viewmodel.ExercisesViewModel


@Composable
fun ExerciseDetails(
    exerciseId: String, exercisesViewModel: ExercisesViewModel
) {
    val context=LocalContext.current
    val exercise by exercisesViewModel.exerciseDetails.collectAsState()
    LaunchedEffect(exerciseId) {
        exercisesViewModel.deleteExercise()
        exercisesViewModel.fetchExerciseById(exerciseId)
    }
    exercise.let { exerciseData ->
        if(exerciseData==null){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading...")
                }
            }
        }else{
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    Text(
                        text = exerciseData.name, style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Body Part: ${exerciseData.bodyPart}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFBB86FC)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Target: ${exerciseData.target}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFBB86FC)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Equipment: ${exerciseData.equipment}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFBB86FC)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Secondary Muscles: ${exerciseData.secondaryMuscles.joinToString(", ")}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFBB86FC)
                    )
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
                    Spacer(modifier = Modifier.height(20.dp))

                    val sharedPref = LocalContext.current.getSharedPreferences(
                        "user_preferences",
                        Context.MODE_PRIVATE

                    )
                    val selectedExercises =
                        sharedPref.getStringSet("selected_exercises", mutableSetOf())?.toMutableSet()
                    val editor = sharedPref.edit()
                    val isSelected = selectedExercises?.contains(exerciseData.id) == true
                    Button(
                        onClick = {
                            if(isSelected){
                                selectedExercises?.remove(exerciseData.id)
                                Toast.makeText(context, "Exercise removed successfully", Toast.LENGTH_SHORT).show()

                            }
                            else{
                                selectedExercises?.add(exerciseData.id)
                                Toast.makeText(context, "Exercise added successfully", Toast.LENGTH_SHORT).show()
                            }
                            editor.putStringSet("selected_exercises", selectedExercises)
                            editor.apply()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Color.Gray else Color(0xFFBB86FC)  ,
                            contentColor = Color.White
                        )
                    ) {
                        Text(

                            text = if (isSelected) "Unselect" else "Select"
                        )
                    }
                    Spacer(modifier = Modifier.padding(bottom = 150.dp))
                }

            }

        }
        }


}
