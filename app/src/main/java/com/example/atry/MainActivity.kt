package com.example.atry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.atry.pages.Calculator
import com.example.atry.pages.ChallengeDetailsScreen
import com.example.atry.pages.ExerciseDetails
import com.example.atry.pages.Explore
import com.example.atry.pages.Food
import com.example.atry.pages.Home
import com.example.atry.pages.Login
import com.example.atry.pages.QuarantineWorkout
import com.example.atry.pages.Setting
import com.example.atry.pages.ShowView
import com.example.atry.pages.SignUp
import com.example.atry.pages.SplashScreen
import com.example.atry.pages.UserProfile
import com.example.atry.pages.WorkoutCategoriesScreen
import com.example.atry.ui.theme.TryTheme
import com.example.atry.viewmodel.AuthViewModel
import com.example.atry.viewmodel.ExercisesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TryTheme {
                Scaffold { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        authViewModel = AuthViewModel(),
                        exercisesViewModel = ExercisesViewModel()
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    exercisesViewModel: ExercisesViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "open_up") {
        composable("open_up") {
            SplashScreen(navController, authViewModel)
        }
        composable("login") {
            Login(modifier, navController, authViewModel)
        }
        composable("sign_up") {
            SignUp(modifier, navController, authViewModel)
        }
        composable("home") {
            Home(navController, exercisesViewModel)
        }
        composable("explore") {
            Explore(modifier, navController)
        }
        composable("profile") {
            UserProfile()
        }
        composable("food") {
            Food()
        }
        composable("setting") {
            Setting(navController , authViewModel)
        }
        composable("exercise_details/{exerciseId}") { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId")
            ExerciseDetails(
                exerciseId = exerciseId ?: "",
                exercisesViewModel = exercisesViewModel
            )
        }
        composable("calculator") {
            Calculator(modifier, navController, authViewModel)
        }
        composable("result_bmi/{bmi}") { backStackEntry ->
            val bmi = backStackEntry.arguments?.getString("bmi")
            ShowView(value = bmi, navController = navController)
        }
        composable("quarantine_workout") {
            QuarantineWorkout()
        }
        composable("challenge_details/{challengeName}") { backStackEntry ->
            val challengeName = backStackEntry.arguments?.getString("challengeName")
            ChallengeDetailsScreen(challengeName = challengeName ?: "")
        }
        composable("workout_categories_screen/{workoutCategoryName}") { backStackEntry ->
            val workoutCategoryName = backStackEntry.arguments?.getString("workoutCategoryName")
            WorkoutCategoriesScreen(workoutCategoryName ?: "", modifier, exercisesViewModel,navController)
        }
    }
}


