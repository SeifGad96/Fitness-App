package com.example.atry.pages


import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    exerciseId: String, exercisesViewModel: ExercisesViewModel,
    onSelectExercise: (Exercise) -> Unit
) {
    val exercise by exercisesViewModel.exerciseDetails.collectAsState()
    LaunchedEffect(exerciseId) {
        exercisesViewModel.fetchExerciseById(exerciseId)
    }

    exercise?.let { exerciseData ->
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
                val editor = sharedPref.edit()
                Button(
                    onClick = {
                        val selectedExercises =
                            sharedPref.getStringSet("selected_exercises", mutableSetOf())
                                ?.toMutableSet()
                        selectedExercises?.add(exerciseData.id)
                        editor.putStringSet("selected_exercises", selectedExercises)
                        editor.apply()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBB86FC),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Select"
                    )
                }
                Spacer(modifier = Modifier.padding(bottom = 150.dp))
            }

        }

    }
}
