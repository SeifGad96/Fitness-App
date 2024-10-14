package com.example.atry.explore

import android.content.Intent
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.atry.R

@Composable
fun QuarantineWorkout(modifier: Modifier) {
    val quarantineExercises = listOf(
        QuarantineExercise(
            "Push-Ups",
            R.drawable.push_ups,
            "https://www.verywellfit.com/the-push-up-exercise-3120574"
        ),
        QuarantineExercise(
            "Squats",
            R.drawable.squatss,
            "https://www.healthline.com/health/exercise-fitness/squats-benefits"
        ),
        QuarantineExercise(
            "Mountain Climbers",
            R.drawable.mountain_climber,
            "https://www.verywellfit.com/mountain-climbers-exercise-3966947"
        ),
        QuarantineExercise(
            "Triceps Dips",
            R.drawable.triceps,
            "https://www.verywellfit.com/the-chair-dip-triceps-exercise-3120734"
        ),
        QuarantineExercise(
            "Bicycle Crunches",
            R.drawable.bicycle_crunches,
            "https://www.verywellfit.com/bicycle-crunch-exercise-3120058"
        ),
        QuarantineExercise(
            "Lunges (each leg)",
            R.drawable.lunges_each_leg,
            "https://barbend.com/lunge/"
        ),
        QuarantineExercise(
            "High Knees",
            R.drawable.high_knees,
            "https://www.healthline.com/health/fitness/high-knees-benefits"
        ),
        QuarantineExercise(
            "Jumping Jacks",
            R.drawable.jumping_jacks,
            "https://www.healthline.com/health/fitness-exercise/jumping-jacks"
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "Best Quarantine Workout:",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(quarantineExercises) { quarantineExercise ->
                QuarantineWorkoutItem(quarantineExercise)
            }
        }
    }

}


@Composable
fun QuarantineWorkoutItem(exercise: QuarantineExercise) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 6.dp)

    ) {
        Card(
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(exercise.image),
                    contentDescription = "meditation pose",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(140.dp, 140.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = exercise.name,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(vertical = 8.dp)

                    )
                    Text(
                        text = "3 sets of 10-15 reps",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    )
                }
            }

        }
        val annotatedString = buildAnnotatedString {
            append("For more information, ")
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
            ) {
                pushStringAnnotation(
                    "link_tag",
                    exercise.url
                )
                append("Click here!")
                pop()
            }
        }
        ClickableText(
            text = annotatedString,
            modifier = Modifier.padding(start = 6.dp, bottom = 4.dp)
        ) { offset ->
            annotatedString.getStringAnnotations(
                tag = "link_tag",
                start = offset,
                end = offset
            )
                .firstOrNull()?.let {
                    val i = Intent(Intent.ACTION_VIEW, it.item.toUri())
                    context.startActivity(i)
                }
        }
    }
}

data class QuarantineExercise(
    val name: String,
    val image: Int,
    val url: String
)
