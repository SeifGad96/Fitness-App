package com.example.atry.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.atry.api.Exercise
import com.example.atry.navigate.BottomNavigationBar
import com.example.atry.viewmodel.ExercisesViewModel


@Composable
fun Home(
    navController: NavController,
    exercisesViewModel: ExercisesViewModel
    ) {
    val exercises by exercisesViewModel.exercises.collectAsState()
    var selectedBodyPart by remember { mutableStateOf("back") }

    LaunchedEffect(selectedBodyPart) {
        exercisesViewModel.fetchExercises(selectedBodyPart, limit = 10, offset = 0)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (exercises.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Text(
                    text = "Hello \uD83D\uDD25",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit.Unspecified,
                    modifier = Modifier.padding(top = 8.dp)

                )
                Text(
                    text = "Popular Workouts",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )


                LazyRow(
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(exercises) { exercise ->
                        WorkoutCard(exercise) {
                            navController.navigate("exercise_details/${exercise.id}")
                        }
                    }
                }
                Text(
                    text = "Today Plan",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyColumn(modifier = Modifier.clickable {
                    navController.navigate("exercise_details")
                }) {
                    items(exercises) { exercise ->
                        TodayPlanItem(
                            exercise
                        ) {
                            navController.navigate("exercise_details/${exercise.id}")

                        }
                    }
                }



            }

        }
        BottomNavigationBar(navController)
    }
}
@Composable
fun WorkoutCard(exercise: Exercise, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
            .size(200.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clickable {
                    onClick()
                }
        ) {
            AsyncImage(
                model = exercise.gifUrl, // Use imageUrl here
                contentDescription = exercise.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)


            )
        }
    }
}



@Composable
fun TodayPlanItem(exercise:Exercise, onClick: () -> Unit) {

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    onClick()
                }
        ) {
            AsyncImage(
                model = exercise.gifUrl,
                contentDescription = exercise?.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = exercise.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "${exercise.equipment}", style = MaterialTheme.typography.titleMedium)

            }
        }

    }
}




