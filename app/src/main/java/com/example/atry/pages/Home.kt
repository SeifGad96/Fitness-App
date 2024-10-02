package com.example.atry.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.atry.AuthState
import com.example.atry.AuthViewModel

@Composable
fun Home(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel){

    val authState = authViewModel.authState.observeAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Page", fontSize = 32.sp)

        Button(onClick = {
            authViewModel.signout()
            navController.navigate("login")
        }) {
            Text(text = "Sign out")
        }
    }

}