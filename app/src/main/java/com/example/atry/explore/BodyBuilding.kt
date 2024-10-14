package com.example.atry.explore

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atry.pages.ExerciseList
import com.example.atry.viewmodel.BodyBuildingViewModel
import com.example.atry.viewmodel.ExercisesViewModel

@Composable
fun BodyBuilding(modifier: Modifier, exerciseViewModel: ExercisesViewModel,navController: NavController) {
    val bodyBuildingViewModel: BodyBuildingViewModel = viewModel()
    val exercises by exerciseViewModel.exercises.collectAsState(emptyList())
    val selectedCategory by bodyBuildingViewModel.selectedCategory.observeAsState("back")
    val categories = listOf(
        "back",
        "chest",
        "shoulders",
        "upper arms",
        "lower arms",
        "upper legs",
        "lower legs"
    )

    LaunchedEffect(selectedCategory) {
        exerciseViewModel.deleteAllExercises()
        Log.d("trace", "recomposition")
        exerciseViewModel.fetchExercises(selectedCategory, limit = 10, offset = 0)
    }
    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 10.dp, vertical = 12.dp)) {
        BodyBuildingCategory(selectedCategory, categories) { category ->
            bodyBuildingViewModel.updateCategory(category)
            Log.d("category", "category in BodyBuildingItem$category")
        }
        if(exercises.isEmpty())
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        else
            ExerciseList(exercises,navController)
    }

}

@Composable
fun BodyBuildingCategory(
    selectedCategory: String, categories: List<String>, onCategoryChange: (String) -> Unit
) {
    LazyRow() {
        items(categories) { category ->
            BodyBuildingCategoryItem(
                category,
                selectedCategory == category
            ) {
                onCategoryChange(category)
            }
        }
    }
}

@Composable
fun BodyBuildingCategoryItem(
    category: String,
    isSelected: Boolean,
    onButtonClick: () -> Unit
) {

    val buttonColor = if (isSelected) Color.Gray else Color.Blue
    Button(
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(buttonColor),
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = category, color = Color.White)
    }
}

