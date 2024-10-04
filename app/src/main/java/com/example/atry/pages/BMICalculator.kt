package com.example.atry.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.atry.viewmodel.AuthViewModel
import com.example.atry.calculatorcomponents.GenderBox
import com.example.atry.calculatorcomponents.HeightAndAge

@Composable
fun Calculator(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    val authState = authViewModel.authState.observeAsState()

    val genderSelectState = remember { mutableStateOf(0) }
    val weightSelectState = remember { mutableStateOf(60) }
    val ageSelectState = remember { mutableStateOf(20) }
    val sliderState = remember { mutableStateOf(130f) }

    Surface(color = Color(9, 12, 34, 1)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            AppBar(text = "BMI CALCULATOR", onTap = {}, color = Color(16,20,39))
            Spacer(modifier = Modifier.height(20.dp)
                )

            // Gender Selection Row
            Row(horizontalArrangement = Arrangement.Center) {
                GenderBox(
                    text = "Male",
                    icon = Icons.Default.Male,
                    color = if (genderSelectState.value == 1) Color.White else Color.White.copy(0.5f),
                    onClick = { genderSelectState.value = 1 }
                )
                Spacer(modifier = Modifier.width(5.dp))
                GenderBox(
                    text = "Female",
                    icon = Icons.Default.Female,
                    color = if (genderSelectState.value == 2) Color.White else Color.White.copy(0.5f),
                    onClick = { genderSelectState.value = 2 }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            HeightSlider(sliderState) // Height slider for BMI calculation

            Spacer(modifier = Modifier.height(20.dp))
            Row {
                HeightAndAge(text = "WEIGHT", value = weightSelectState)
                HeightAndAge(text = "AGE", value = ageSelectState)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Calculate BMI Button
            AppBar(text = "CALCULATE YOUR BMI", onTap = {
                val value = (weightSelectState.value / (sliderState.value * sliderState.value)) * 10000
                val lastValue = "%.0f".format(value) // Formatting the BMI value
                navController.navigate("result_bmi/$lastValue") // Navigate to the second page
            }, color = Color(234, 21, 86))

        }
    }
}


@Composable
fun HeightSlider(
    sliderStateLocal: MutableState<Float>
) {

    Box(modifier = Modifier
        .height(180.dp)
        .width(365.dp)
        .background(Color(16, 20, 39))) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)) {
            Text(text = "HEIGHT", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold,
                color = Color.White.copy(0.5f))
            Row() {
                val height = "%.0f".format(sliderStateLocal.value)
                Text(text = "$height", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text(text = "cm", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(0.5f),
                    modifier = Modifier.padding(top = 28.dp), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Slider(value = sliderStateLocal.value, onValueChange = {
                sliderStateLocal.value = it
            }, modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                valueRange = 30f..230f, colors = SliderDefaults.colors(thumbColor = Color(234,21,86)
                    , inactiveTrackColor = Color.White.copy(0.5f), activeTrackColor = Color.White),
            )
        }
    }
}

@Composable
fun AppBar(
    text: String,
    onTap: () -> Unit,
    color: Color,
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 70.dp).background(color = color).clickable { onTap.invoke() }
    ) {
        Text(text = text, fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center, style =
            MaterialTheme.typography.bodyLarge, color = Color.White)
    }
}

@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colorScheme.background, // Use Material3's colorScheme
    shadowElevation: Dp = 4.dp
) {
    Card(
        modifier = modifier
            .padding(all = 4.dp)
            .size(60.dp)
            .clickable { onClick.invoke() },
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor), // Set the background color using containerColor
        elevation = CardDefaults.cardElevation(defaultElevation = shadowElevation) // Set elevation using shadowElevation
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Icon",
            tint = tint,
            modifier = Modifier.padding(16.dp)
        )
    }
}
//@Preview(showSystemUi = true)
//@Composable
//fun BMIPreview() {
//    Calculator()
//}