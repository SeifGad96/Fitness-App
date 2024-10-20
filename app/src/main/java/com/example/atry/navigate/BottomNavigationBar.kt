package com.example.atry.navigate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.atry.R
@Composable
fun BottomNavigationBar(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(30.dp)
                .padding(bottom = 20.dp),
            color = Color.White,
            elevation = 4.dp
        ) {
            BottomNavigation(
                modifier = Modifier.background(color = Color.Blue)
            ) {
                val currentRoute = navController.currentDestination?.route

                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_home),
                            contentDescription = "Home",
                            tint = if (currentRoute == "home") Color.White else Color.Gray
                        )
                    },
                    selected = currentRoute == "home",
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_explore),
                            contentDescription = "Explore",
                            tint = if (currentRoute == "explore") Color.White else Color.Gray
                        )
                    },
                    selected = currentRoute == "explore",
                    onClick = {
                        navController.navigate("explore") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_food_bank_24),
                            contentDescription = "Food",
                            tint = if (currentRoute == "food") Color.White else Color.Gray
                        )
                    },
                    selected = currentRoute == "food",
                    onClick = {
                        navController.navigate("food") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = "Setting",
                            tint = if (currentRoute == "setting_screen") Color.White else Color.Gray
                        )
                    },
                    selected = currentRoute == "setting_screen",
                    onClick = {
                        navController.navigate("setting_screen") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_person),
                            contentDescription = "User Profile",
                            tint = if (currentRoute == "profile") Color.White else Color.Gray
                        )
                    },
                    selected = currentRoute == "profile",
                    onClick = {
                        navController.navigate("profile") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}