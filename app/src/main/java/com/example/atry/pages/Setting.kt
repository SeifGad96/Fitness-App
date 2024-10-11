package com.example.atry.pages
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atry.viewmodel.AuthViewModel


@Composable
fun Setting(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Button(onClick = {
            authViewModel.signOut()  // Call sign-out
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }  // Remove home screen from back stack
            }
        }) {
            Text(text = "Sign Out")
        }
    }
}