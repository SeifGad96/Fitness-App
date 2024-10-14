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
fun YogaExercises(modifier: Modifier) {
    val yogaExercises = listOf(
        YogaExercise(
            "meditation pose",
            R.drawable.lotus_pose,
            "https://www.artofliving.org/us-en/meditation/how-to/meditation-positions",
            "10 to 15 minutes"
        ),
        YogaExercise(
            "warrior pose",
            R.drawable.warior_pose,
            "https://www.arhantayoga.org/blog/yoga-warrior-pose-guide/",
            "1 to 5 minutes"
        ),
        YogaExercise(
            "child pose",
            R.drawable.child_pose,
            "https://www.yogabasics.com/asana/child/",
            "2 to 8 minutes"
        ),
        YogaExercise(
            "bow pose",
            R.drawable.bow_pose,
            "https://www.artofliving.org/in-en/yoga/yoga-poses/dhanurasana-bow-pose",
            "1 to 5 minutes"
        ),
        YogaExercise(
            "eagle pose",
            R.drawable.eagle_pose,
            "https://www.ekhartyoga.com/resources/yoga-poses/eagle-pose",
            "1 to 3 minutes"
        ),
        YogaExercise(
            "fish pose",
            R.drawable.fish_pose,
            "https://www.yogajournal.com/poses/fish-pose/",
            "5 to 10 minutes"
        ),
        YogaExercise(
            "pigeon pose",
            R.drawable.pigeon_pose,
            "https://jasonyoga.com/2016/06/27/eka-pada-rajakapotasana/",
            "1 to 5 minutes"
        ),
        YogaExercise(
            "half moon pose",
            R.drawable.half_moon_pose,
            "https://www.yogajournal.com/poses/half-moon-pose-3/",
            "1 to 3 minutes"
        ),
        YogaExercise(
            "seated forward pose",
            R.drawable.seated_forwar_pose,
            "https://www.ekhartyoga.com/resources/yoga-poses/seated-forward-bend",
            "2 to 8 minutes"
        ),
    )
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            items(yogaExercises) { yogaExercise ->
                YogaExerciseItem(yogaExercise)
            }
        }
    }

}

@Composable
fun YogaExerciseItem(yogaExercise: YogaExercise) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 6.dp)

    ) {
        Card(
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(yogaExercise.image),
                    contentDescription = "meditation pose",
                    modifier = Modifier
                        .size(100.dp, 120.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = yogaExercise.name,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(vertical = 8.dp)

                    )
                    Text(
                        text = "${yogaExercise.time}",
                        fontSize = 16.sp,
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
                    yogaExercise.link
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

data class YogaExercise(val name: String, val image: Int, val link: String, val time: String)





