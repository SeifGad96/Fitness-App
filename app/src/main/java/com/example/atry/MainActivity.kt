package com.example.atry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.atry.pages.Calculator
import com.example.atry.pages.ExerciseDetails

import com.example.atry.pages.Explore
import com.example.atry.pages.Food
import com.example.atry.pages.Home
import com.example.atry.pages.Login
import com.example.atry.pages.Setting
import com.example.atry.pages.ShowView
import com.example.atry.pages.SignUp
import com.example.atry.pages.UserProfile

import com.example.atry.ui.theme.TryTheme
import com.example.atry.viewmodel.AuthViewModel
import com.example.atry.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {



            Scaffold { innerPadding ->
                AppNavigation(
                    modifier = Modifier.padding(innerPadding),
                    authViewModel = AuthViewModel(),
                    homeViewModel = HomeViewModel()
                )
            }
            Root {
                AppNavigation(authViewModel = AuthViewModel(), homeViewModel = HomeViewModel())
            }


        }
    }
}

@Composable
fun Root(content: @Composable () -> Unit) {
    TryTheme {

        Surface(
            color = Color(255, 255, 255)
        ) {
            content()
        }
    }
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(modifier, navController, authViewModel)
        }
        composable("sign_up") {
            SignUp(modifier, navController, authViewModel)
        }
        composable("home") {
            Home( navController, homeViewModel)
        }
        composable("explore") {
            Explore()
        }
        composable("profile") {
            UserProfile()
        }
        composable("food") {
            Food()
        }
        composable("setting") {
            Setting()
        }
        composable("exercise_details/{exerciseId}") { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId")
            ExerciseDetails(
                navController = navController,
                exerciseId = exerciseId ?: "",
                viewModel = homeViewModel
            )
        }

        composable("calculator") {
            Calculator(modifier, navController, authViewModel)
        }
        composable("result_bmi/{bmi}") { backStackEntry ->
            val bmi = backStackEntry.arguments?.getString("bmi")
            ShowView(value = bmi, navController = navController)
        }
    }
}


