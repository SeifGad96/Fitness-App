package com.example.atry.pages

import Back_Handler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.atry.R
import com.example.atry.navigate.BottomNavigationBar

data class Challenge(val name: String, val image: Int)

@Composable
fun Explore(modifier: Modifier = Modifier, navController: NavController) {
    val categories = listOf(
        "Cardio",
        "Body Building",
        "Stretching",
        "Yoga"
    )
    val challenges = listOf(
        Challenge("Plank Challenge", R.drawable.plank),
        Challenge("Squat Challenge", R.drawable.squats),
        Challenge("Spirit Challenge", R.drawable.spirit),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        QuarantineDesign(navController)
        Text(
            text = "Best for you",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 columns grid
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                CategoryCardItem(category) {
                    navController.navigate("workout_categories_screen/$category")
                }
            }
        }
        Text(
            text = "Challenge",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        ChallengeList(navController, challenges)
    }
    BottomNavigationBar(navController)
    Back_Handler()
}

@Composable
fun QuarantineDesign(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .clickable {
                navController.navigate("quarantine_workout")
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.home_workout_img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Text(
                text = "Best Quarantine\nWorkout",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)

            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = "See more",
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Image(
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = "See more arrow",
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

@Composable
fun ChallengeList(navController: NavController, challenges: List<Challenge>) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
    ) {
        items(challenges) { challenge ->
            ChallengeItem(challenge) {
                navController.navigate("challenge_details/${challenge.name}")
            }
        }
    }
}

@Composable
fun ChallengeItem(challenge: Challenge, onCardClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .size(120.dp)
            .clickable(onClick = onCardClick)

    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(challenge.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.1f))
                    .padding(4.dp)
            ) {
                Text(
                    text = challenge.name,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

        }

    }
}

@Composable
fun CategoryCardItem(category: String, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f) // Make items square-shaped
            .padding(4.dp)
            .clickable(onClick = onCardClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = category)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun showExplore() {
    Explore(navController = rememberNavController())
}




