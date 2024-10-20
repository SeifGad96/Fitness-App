package com.example.atry.pages

import Back_Handler
import android.media.Image
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.atry.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atry.navigate.BottomNavigationBar
import com.example.atry.viewmodel.AuthViewModel


@Composable
fun UserProfile(navController: NavController) {
    val authViewModel: AuthViewModel = viewModel()
    val notification = rememberSaveable { mutableStateOf("") }
    val userData by authViewModel.userData.observeAsState()

    // Fetch user data when the Composable is loaded
    LaunchedEffect(Unit) {
        authViewModel.getUserData()
    }

    if (notification.value.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
    }

    var weight by rememberSaveable { mutableStateOf(userData?.weight?.toString() ?: "") }
    var height by rememberSaveable { mutableStateOf(userData?.height?.toString() ?: "") }
    var age by rememberSaveable { mutableStateOf(userData?.age?.toString() ?: "") }
    var gender by rememberSaveable { mutableStateOf(userData?.gender?.toString() ?: "") }
    var bmi by rememberSaveable { mutableStateOf(userData?.bmi ?: "") }

    // Update UI when userData changes
    LaunchedEffect(userData) {
        userData?.let {
            weight = it.weight?.toString() ?: ""
            height = it.height?.toString() ?: ""
            age = it.age?.toString() ?: ""
            gender = it.gender?.toString() ?: ""
            bmi = it.bmi ?: ""
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Cancel",
                    modifier = Modifier.clickable { notification.value = "Cancelled" }
                )
                Text(
                    text = "Save",
                    modifier = Modifier.clickable {
                        val weightValue = weight.toIntOrNull() ?: 0
                        val heightValue = height.toFloatOrNull() ?: 0.0f
                        val ageValue = age.toIntOrNull() ?: 0
                        val genderValue = gender.toIntOrNull() ?: 0
                        authViewModel.saveUserData(weightValue, heightValue, ageValue, genderValue, bmi)
                        notification.value = "Profile updated"
                    }
                )
            }

            ProfileImage()

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Weight", modifier = Modifier.width(100.dp))
                TextField(
                    value = weight,
                    onValueChange = { weight = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.Black
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Height", modifier = Modifier.width(100.dp))
                TextField(
                    value = height,
                    onValueChange = { height = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.Black
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Age", modifier = Modifier.width(100.dp))
                TextField(
                    value = age,
                    onValueChange = { age = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.Black
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Gender", modifier = Modifier.width(100.dp))
                TextField(
                    value = gender,
                    onValueChange = { gender = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.Black
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "BMI", modifier = Modifier.width(100.dp))
                TextField(
                    value = bmi,
                    onValueChange = { bmi = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.Black
                    )
                )
            }
        }

        BottomNavigationBar(navController = navController)
        Back_Handler()
    }
}



@Composable
fun ProfileImage() {
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter: Painter = rememberAsyncImagePainter(
        if (imageUri.value.isEmpty())
            R.drawable.ic_user
        else
            imageUri.value
    )
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()
    ) {
            uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
    }

    Column(modifier = Modifier
        .padding(0.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        }
        Text(text = "Change profile picture")
    }
}