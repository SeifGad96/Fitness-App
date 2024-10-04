package com.example.atry.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun ShowView(value: String?, modifier: Modifier = Modifier, navController: NavController) {
    val bmi = value?.toInt() ?: 10
    val textList = arrayListOf("You are under Weight", "You are at a healthy weight.", "You are overweight.", "You are obese.")
    val headlineList = arrayListOf("UNDERWEIGHT", "NORMAL", "OVERWEIGHT", "OBESE")

    val index = if (bmi < 18) {0}
    else if (bmi >= 18.5 && bmi < 25) {
        1
    } else if (bmi > 25 && bmi <= 29.99) {
        2
    } else {
        3
    }

    Surface() {
        Column(modifier = Modifier.background(color = Color(9, 12, 34))) {
            AppBar(text = "BMI CALCULATOR", onTap = {}, color = Color(16, 20, 39))
            Text(
                text = "Your Result",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 20.dp, top = 25.dp, bottom = 15.dp)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .background(color = Color(16, 20, 39))
                    .fillMaxWidth()
                    .height(520.dp), contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = headlineList[index],
                        color = Color.Green,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = bmi.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = "Normal BMI range:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(0.5f)
                    )
                    Text(
                        text = "18 - 25 kg/m2",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = textList[index],
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(60.dp))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(260.dp)
                            .height(80.dp)
                            .background(color = Color(9, 12, 34))
                    ) {
                        AppBar(
                            text = "START YOUR TRAINING :)",
                            onTap = {
                                navController.navigate("home")
                            },
                            color = Color(66, 165, 245)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            AppBar(
                text = "RE-CALCULATE YOUR BMI",
                onTap = { navController.navigate("calculator") },
                color = Color(234, 21, 86)
            )

        }
    }
}

