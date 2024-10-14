package com.example.atry.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.atry.R
import com.example.atry.api.Exercise


@Composable
fun ExerciseList(exercises: List<Exercise>, navController: NavController) {
    LazyColumn {
        items(exercises) { exercise ->
            ExerciseItem(exercise, navController)
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise,navController: NavController) {

    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = exercise.gifUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 100.dp, 140.dp)
                    .clip(RoundedCornerShape(24.dp))
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f),
            ) {
                Text(
                    text = exercise.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    maxLines = 1, // Limit to 1 line
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 6.dp)
                )
                Text(
                    text = "3X10 reps",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(text = "equipment: ${exercise.equipment}")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 16.dp)
                    .clickable {
                        navController.navigate("exercise_details/${exercise.id}")
                    }
            ) {
                Text(
                    text = "See more",
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(end = 4.dp)
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
