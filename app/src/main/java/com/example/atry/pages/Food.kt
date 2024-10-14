package com.example.atry.pages

import androidx.compose.runtime.Composable
import com.example.atry.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.atry.navigate.BottomNavigationBar

data class Meal(
    val imageRes: Int,
    val title: String,
    val description: String
)

@Composable
fun HealthyFoodPage(modifier: Modifier = Modifier,navController: NavController) {
    val meals = listOf(
        Meal(
            imageRes = R.drawable.grilled_chicken,
            title = "Grilled Chicken with Veggies",
            description = "A high-protein meal with grilled chicken and steamed vegetables, rich in vitamins and low in carbs."
        ),
        Meal(
            imageRes = R.drawable.salmon_salad,
            title = "Salmon Salad",
            description = "Fresh salad with smoked salmon, avocado, and mixed greens. A perfect source of healthy fats and omega-3."
        ),
        Meal(
            imageRes = R.drawable.quinoa_bowl,
            title = "Quinoa and Black Bean Bowl",
            description = "A nutrient-packed meal with quinoa, black beans, avocado, and veggies. Full of fiber and plant-based protein."
        ),
        Meal(
            imageRes = R.drawable.avocado_toast,
            title = "Avocado Toast with Egg",
            description = "Whole grain toast topped with mashed avocado and a poached egg. A great breakfast rich in fiber and protein."
        ),
        Meal(
            imageRes = R.drawable.oatmeal_fruit,
            title = "Oatmeal with Berries",
            description = "A healthy bowl of oatmeal topped with fresh berries, nuts, and honey. A perfect meal for sustained energy."
        ),
        Meal(
            imageRes = R.drawable.chicken_stir_fry,
            title = "Chicken Stir-Fry",
            description = "Tender chicken breast stir-fried with colorful bell peppers, broccoli, and a savory sauce. A quick, healthy meal rich in protein and vitamins."
        ),
        Meal(
            imageRes = R.drawable.lentil_soup,
            title = "Lentil Soup",
            description = "A hearty lentil soup made with fresh vegetables and spices. Packed with protein, fiber, and nutrients for a comforting meal."
        ),
        Meal(
            imageRes = R.drawable.turkey_wrap,
            title = "Turkey and Hummus Wrap",
            description = "Whole wheat wrap filled with sliced turkey, hummus, spinach, and bell peppers. A delicious, portable meal high in protein and fiber."
        )

    )

    // Apply green background to the page
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary) // Set secondary color (white) as background
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(meals.size) { index ->
                MealItem(meal = meals[index])
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    BottomNavigationBar(navController)
}

@Composable
fun MealItem(meal: Meal) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary) // Background with secondary color (white)
            .padding(8.dp)
    ) {
        // Meal Image
        Image(
            painter = painterResource(id = meal.imageRes),
            contentDescription = meal.title,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Meal Title with Primary Green color
        Text(
            text = meal.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary // Set primary color (green) for the title
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Meal Description with default text color
        Text(
            text = meal.description,
            fontSize = 16.sp,
            color = Color.Gray // You can change this if needed
        )
    }
}
