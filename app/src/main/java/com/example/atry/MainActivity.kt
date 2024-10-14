package com.example.atry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.atry.Setting.DetailScreen
import com.example.atry.Setting.EditProfile
import com.example.atry.Setting.Notification
import com.example.atry.Setting.Setting
import com.example.atry.Setting.SettingsList
import com.example.atry.pages.Calculator
import com.example.atry.explore.ChallengeDetailsScreen
import com.example.atry.pages.ExerciseDetails
import com.example.atry.explore.Explore
import com.example.atry.pages.Home
import com.example.atry.pages.Login
import com.example.atry.explore.QuarantineWorkout
import com.example.atry.pages.ShowView
import com.example.atry.pages.SignUp
import com.example.atry.pages.UserProfile
import com.example.atry.explore.WorkoutCategoriesScreen
import com.example.atry.pages.HealthyFoodPage
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

    val context= LocalContext.current
    NavHost(navController = navController, startDestination = "login") {
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
            HealthyFoodPage(modifier,navController)
        }
        composable("setting") {
            Setting( authViewModel)

        }
        composable("exercise_details/{exerciseId}") { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId")
            ExerciseDetails(
                exerciseId = exerciseId ?: "",
                exercisesViewModel = exercisesViewModel
            )
        }
        composable("calculator") {
            Calculator( navController, authViewModel)
        }
        composable("result_bmi/{bmi}") { backStackEntry ->
            val bmi = backStackEntry.arguments?.getString("bmi")
            ShowView(value = bmi, navController = navController)
        }
        composable("quarantine_workout") {
            QuarantineWorkout(modifier)
        }
        composable("challenge_details/{challengeName}") { backStackEntry ->
            val challengeName = backStackEntry.arguments?.getString("challengeName")
            ChallengeDetailsScreen(challengeName = challengeName ?: "")
        }
        composable("workout_categories_screen/{workoutCategoryName}") { backStackEntry ->
            val workoutCategoryName = backStackEntry.arguments?.getString("workoutCategoryName")
            WorkoutCategoriesScreen(workoutCategoryName ?: "", modifier, exercisesViewModel,navController)
        }

        //////////////////////////////
        composable("setting_screen") { SettingsList(navController, authViewModel) }
        composable("edit") { EditProfile(authViewModel,context,navController) }
        composable("notification") { Notification() }
        composable("changePassword") { DetailScreen("Change_Password", context) }
        composable("logout") {
            Login(
                navController = navController,
                authViewModel = AuthViewModel()
            )
        }
    }
}


