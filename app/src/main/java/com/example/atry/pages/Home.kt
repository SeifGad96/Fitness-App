package com.example.atry.pages

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.atry.R
import com.example.atry.api.Exercise
import com.example.atry.viewmodel.AuthViewModel
import com.example.atry.viewmodel.HomeViewModel
import androidx.compose.runtime.collectAsState


@Composable
fun Home(
    navController: NavController,
    homeViewModel: HomeViewModel,
    ) {
    val exercises by homeViewModel.exercises.collectAsState()
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
                            exerciseName = exercise.name,
                            gifUrl = exercise.gifUrl,
                            equipment = exercise.equipment
                        ) {
                            navController.navigate("exercise_details/${exercise.id}")

                        }
                    }
                }

                BottomNavigationBar(navController)

            }

        }
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

var exercise: Exercise? = null

@Composable
fun TodayPlanItem(exerciseName: String, gifUrl: String, equipment: String, onClick: () -> Unit) {

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
                model = gifUrl,
                contentDescription = exercise?.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = exerciseName, style = MaterialTheme.typography.titleMedium)
                Text(text = "$equipment", style = MaterialTheme.typography.titleMedium)

            }
        }

    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(30.dp)
                .padding(bottom = 20.dp),
            color = Color.White,
            elevation = 4.dp
        ) {
            BottomNavigation(
                modifier = Modifier.background(color = Color.Blue)
            ) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_home),
                            contentDescription = "Home"
                        )
                    },
                    selected = true,
                    onClick = {
                        navController.navigate("home")
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_explore),
                            contentDescription = "Explore"
                        )
                    },
                    selected = false,
                    onClick = {
                        navController.navigate("explore")
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_food_bank_24),
                            contentDescription = "Food"
                        )
                    },
                    selected = false,
                    onClick = {
                        navController.navigate("food")
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = "Setting"
                        )
                    },
                    selected = false,
                    onClick = {
                        navController.navigate("setting")
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_person),
                            contentDescription = "User Profile"
                        )
                    },
                    selected = false,
                    onClick = {
                        navController.navigate("profile")
                    }
                )
            }
        }
    }

}


