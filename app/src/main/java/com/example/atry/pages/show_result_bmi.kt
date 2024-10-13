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
    val textList = arrayListOf("You are underweight", "You are at a healthy weight", "You are overweight", "You are obese")
    val headlineList = arrayListOf("UNDERWEIGHT", "NORMAL", "OVERWEIGHT", "OBESE")

    val index = when {
        bmi < 18 -> 0
        bmi in 18..24 -> 1
        bmi in 25..29 -> 2
        else -> 3
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column {
            AppBar(
                text = "BMI CALCULATOR",
                onTap = {},
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Your Result",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 20.dp, top = 25.dp, bottom = 15.dp)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .fillMaxWidth()
                    .height(520.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = headlineList[index],
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = bmi.toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "Normal BMI range:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )

                    Text(
                        text = "18 - 25 kg/m2",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = textList[index],
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(60.dp))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(260.dp)
                            .height(80.dp)
                            .background(color = MaterialTheme.colorScheme.background)
                    ) {
                        AppBar(
                            text = "START YOUR TRAINING :)",
                            onTap = { navController.navigate("home") },
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            AppBar(
                text = "RE-CALCULATE YOUR BMI",
                onTap = { navController.navigate("calculator") },
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}


