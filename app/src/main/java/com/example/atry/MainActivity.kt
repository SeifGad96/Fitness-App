package com.example.atry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.atry.pages.Home
import com.example.atry.pages.Login
import com.example.atry.pages.ShowView
import com.example.atry.pages.SignUp
import com.example.atry.ui.theme.TryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel: AuthViewModel by viewModels()
        setContent {
            AppNavigation(authViewModel = AuthViewModel())

            Root {
                AppNavigation(authViewModel = AuthViewModel())
            }

        }
    }
}

@Composable
fun Root(content: @Composable () -> Unit) {
    TryTheme {

        Surface(
            color = Color(9,12,34)
        ) {
            content()
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier,authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(modifier, navController, authViewModel)
        }
        composable("sign_up") {
            SignUp(modifier, navController, authViewModel)
        }
        composable("home") {
            Home(modifier, navController, authViewModel)
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