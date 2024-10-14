package com.example.atry.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atry.R

@Composable
fun StretchingExercises(modifier: Modifier) {

    val stretchingExercises = listOf(
        StretchingExercise("Shoulder Hold", R.drawable.shoulder_hold),
        StretchingExercise("Pat on the pack", R.drawable.pat_on_the_back),
        StretchingExercise("wrist extension", R.drawable.wrist_extensor),
        StretchingExercise("Bend over chest", R.drawable.bend_over_chest_stretch),
        StretchingExercise("Standing quad", R.drawable.standing_quad),
        StretchingExercise("Leg stretch", R.drawable.leg_stretch),
        StretchingExercise("Bridge pose stretch", R.drawable.bridge_pose)
    )
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(stretchingExercises) { stretchingExercise ->
            StretchingExerciseItem(exercise = stretchingExercise)
        }
    }
}


@Composable
fun StretchingExerciseItem(exercise: StretchingExercise) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(exercise.image),
                contentDescription = "meditation pose",
                modifier = Modifier
                    .size(100.dp, 120.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = exercise.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(vertical = 8.dp)

                )
                Text(
                    text = "Stay for 20-40 seconds",
                    fontSize = 16.sp,
                )
            }
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    StretchingExercises(modifier = Modifier)
}

data class StretchingExercise(
    val name: String,
    val image: Int
)