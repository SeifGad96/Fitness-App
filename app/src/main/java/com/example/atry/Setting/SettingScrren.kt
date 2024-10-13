package com.example.atry.Setting

import Back_Handler
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.work.WorkManager
import com.example.atry.navigate.BottomNavigationBar
import com.example.atry.viewmodel.AuthViewModel
import com.example.atry.Setting.DetailScreen as DetailScreen1


@Composable
fun Setting(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    navController.navigate("setting_screen")
}

@Composable
fun SettingsList(navController: NavController, authViewModel: AuthViewModel) {
    BottomNavigationBar(navController)
    Back_Handler()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 110.dp)
            .background(Color(0xFFF5F5F5))
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp)) // مسافة بين الصندوقين

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Setting",
                    color = Color(0xFF000000),
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 30.sp,
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // مسافة بين الصندوقين
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    SettingsOption(
                        "Edit profile",
                        Icons.Default.Edit,
                        Color(0xFFF55600),
                        navController,
                        "edit",
                        authViewModel
                    )
                    //
                    // SettingsOption("Change Password", Icons.Default.ChangeCircle, Color(0xFF673AB7), navController, "changePassword")
                    SettingsOption(
                        "Remember to drink water",
                        Icons.Default.NotificationsActive,
                        Color(0xFF2B60FF),
                        //  navController,
                        //   "notification"
                    )

                    SettingsOption(
                        "App Permissions",
                        Icons.Default.HealthAndSafety,
                        Color(0xFFCDDC39)
                    )
                    SettingsOption("Online Coaching", Icons.Default.Whatsapp, Color(0xFF23B912))
                    SettingsOption(
                        "Logout",
                        Icons.Default.Logout,
                        Color(0xFF000000),
                        navController,
                        "logout",
                        authViewModel
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // مسافة بين الصندوقين
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    SettingsOption("Share", Icons.Default.Share, Color(0xFF448AFF))
                    SettingsOption("Rate App", Icons.Default.Star, Color(0xFFFFC107))
                    SettingsOption("Send feedback", Icons.Default.Message, Color.Gray)
                    SettingsOption("Remove ads", Icons.Default.DoNotDisturb, Color(0xFF1A237E))
                }
            }
        }

        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
            ) {
                Text(
                    text = "Version 1.0.0",
                    color = Color.Gray,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 15.sp,
                )
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun SettingsOption(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { DetailScreen1(title = title, context) },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,  // لون الأيقونة
            modifier = Modifier
                .size(35.dp)
                .background(
                    iconColor,
                    shape = CutCornerShape(4.dp)
                )  // لون برتقالي مع قص الزوايا بشكل بسيطRoundedCornerShape(10.dp)
                .padding(5.dp)
        )// حجم الأيقونة)
        Text(
            text = title,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)

        )
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // استرجاع القيمة المخزنة من SharedPreferences
        var isChecked by remember { mutableStateOf(sharedPreferences.getBoolean("notification_switch", false)) }

        if (title == "Remember to drink water") {
            var isChecked by remember { mutableStateOf(false) }
            val context = LocalContext.current

            // Load initial switch state from SharedPreferences if needed
            val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            isChecked = sharedPreferences.getBoolean("notification_switch", false)

            Switch(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    // Save the new state in SharedPreferences
                    sharedPreferences.edit().putBoolean("notification_switch", isChecked).apply()

                    // Start or stop notifications based on switch state
                    if (isChecked) {
                        startRepeatingNotifications(context)
                    } else {
                        stopRepeatingNotifications(context)
                    }

                },
                thumbContent = {
                    if (isChecked) {
                        Notification()
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                }
            )
        }

        if (title == "Share") {
            IconButton(
                onClick = {
                    SharewhatsApp(context)
                },
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(align = Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Default.Whatsapp, // استخدام imageVector بدلاً من painter
                    contentDescription = "Share With WhatsApp",
                    tint = Color(0xFF3CBD42),
                    modifier = Modifier.size(30.dp)
                )

            }
        }

    }

}




@Composable
fun SettingsOption(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    navController: NavController,
    route: String,
    authViewModel: AuthViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                if (title == "Logout") {
                    Logout(navController, authViewModel,)
                } else {
                    navController.navigate("$route")
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,  // لون الأيقونة
            modifier = Modifier
                .size(35.dp)
                .background(
                    iconColor,
                    shape = CutCornerShape(4.dp)
                )  // لون برتقالي مع قص الزوايا بشكل بسيطRoundedCornerShape(10.dp)
                .padding(5.dp)// حجم الأيقونة
        )

        Text(
            text = title,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)  // المسافة من الجوانب
        )
        if (title != "Logout") {

            Icon(
                imageVector = Icons.Rounded.ArrowForwardIos,
                tint = Color.Gray,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )


        }


    }

}


fun DetailScreen(title: String, c: Context): Unit {

    //val c= LocalContext.current
    when (title) {
        "Share" -> SharerScreen(c)
        "Rate App" -> RateScreen(c)
        "Send feedback" -> FeedpackScreen(c)
        "App Permissions" -> PermationScreen(c)
        "Online Coaching" -> onlineCoaching(c)
        //"logout"-> Logout(c)
    }
}

fun SharewhatsApp(context: Context) {
    try {
        val sendIntent = Intent()
        sendIntent.setAction(Intent.ACTION_SEND)
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "https://play.google.com/store/search?q=fitness%20App&c=apps&hl=en"
        )
        sendIntent.setType("text/plain")
        sendIntent.setPackage("com.whatsapp")
        context.startActivity(Intent.createChooser(sendIntent, ""))
        context.startActivity(sendIntent)
    } catch (e: Exception) {
        // في حالة عدم وجود تطبيق WhatsApp على الجهاز
        e.printStackTrace()
        Toast.makeText(context, "WhatsApp is not installed on this device", Toast.LENGTH_SHORT)
            .show()
    }
}

fun PermationScreen(context: Context) {
    val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    i.data = Uri.fromParts("package", "com.example.atry", null)
    context.startActivity(i)

}

fun onlineCoaching(context: Context) {
    val phoneNumber = "+201104796306" // رقم الهاتف مع رمز البلد
    val message = "Hellow"
    try {
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=$message")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.whatsapp") // يحدد تطبيق WhatsApp فقط
        context.startActivity(intent)
    } catch (e: Exception) {
        // في حالة عدم وجود تطبيق WhatsApp على الجهاز
        e.printStackTrace()
        Toast.makeText(context, "WhatsApp is not installed on this device", Toast.LENGTH_SHORT)
            .show()
    }
}

fun SharerScreen(context: Context) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "https://play.google.com/store/search?q=fitness%20App&c=apps&hl=en"
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}
fun Context.getActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}
fun Logout(navController: NavController, authViewModel: AuthViewModel) {
    authViewModel.signOut()

    // Firebase.auth.signOut()

    navController.navigate("logout") {

        popUpTo(0) { inclusive = true }

    }
    //val context = LocalContext.current.getActivity()!!

        //context.finish()

}

fun RateScreen(context: Context) {
    val link: Uri = "https://play.google.com/store/search?q=fitness%20App&c=apps&hl=en".toUri()
    val i = Intent(Intent.ACTION_VIEW, link)
    context.startActivity(i)
}

fun FeedpackScreen(context: Context) {
    try {
        val emailIntent = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "es1amemad9143@gmail.com", null
            )
        )
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is my subject text")
        context.startActivity(Intent.createChooser(emailIntent, null))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Google Email is not installed", Toast.LENGTH_SHORT).show();
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                "https://play.google.com/store/apps/details?id=com.google.android.gm.lite&hl=en".toUri()
            )
        )

    }

}

@Composable
fun MyComposeTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

@Preview
@Composable
fun SettingScrrenPreview() {
    Setting(authViewModel = AuthViewModel())
}

