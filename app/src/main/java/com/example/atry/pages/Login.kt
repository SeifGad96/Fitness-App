package com.example.atry.pages
import android.util.Patterns
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import com.example.atry.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.atry.viewmodel.AuthViewModel


@Composable
fun Login(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val toastMessage by authViewModel._toastMessage.observeAsState()
    var emailError by remember { mutableStateOf(false) }
    val scale = remember { Animatable(1f) }
    var isLoading by remember { mutableStateOf(false) }

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val surfaceColor = MaterialTheme.colorScheme.surface
    val backgroundColor = MaterialTheme.colorScheme.background
    val errorColor = MaterialTheme.colorScheme.error

    toastMessage?.let { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        authViewModel.clearToastMessage()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

//            Image(
//                painter = painterResource(id = R.drawable.logo),
//                contentDescription = "App Logo",
//                modifier = Modifier.size(70.dp)
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.headlineMedium,
                color = primaryColor,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = surfaceColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    var emailError by remember { mutableStateOf(false) }

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon")
                        },
                        isError = emailError,
                        shape = RoundedCornerShape(12.dp)
                    )

                    if (emailError) {
                        Text(
                            text = "Invalid email address",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }


                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = image,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        }
                    )

                    Text(
                        text = "Forgot Password?",
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable {
                                if (email.isNotEmpty()) {
                                    authViewModel.sendPasswordResetEmail(email)
                                    Toast.makeText(context, "Password reset email sent to $email", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                                }
                            },
                        color = primaryColor,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    isLoading = true
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        authViewModel.logIn(email, password)
                        navController.navigate("home")

                    } else {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(text = "Login", style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append("Don't have an account? ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Sign up")
                    }
                },
                modifier = Modifier.clickable {
                    navController.navigate("sign_up") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}




/*@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    Login()
}*/