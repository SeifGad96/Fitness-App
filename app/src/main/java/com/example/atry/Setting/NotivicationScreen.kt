package com.example.atry.Setting
/*import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.atry.R
import com.example.atry.ui.theme.TryTheme
import java.util.concurrent.TimeUnit

class NotivicationScreen : ComponentActivity() {
    private var showPermissionDeniedDiatog = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        val handel = handelPermissionResponse(this)

        // تسجيل قنوات الإشعار
        notificationChanel()
        notificationChanel2()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TryTheme {
                // نستخدم Column لترتيب العناصر بشكل عمودي
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (showPermissionDeniedDiatog.value)
                        PermissionDeniedDiaLog {
                            showPermissionDeniedDiatog.value = false
                        }

                    val context = LocalContext.current
                    Button(onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            handel.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            sentNotification(context)
                        }
                    }) {
                        Text(text = "Notify my")
                    }
                }
            }
        }
    }


    private fun handelPermissionResponse(c: Context): ActivityResultLauncher<String> {
        val permissionLancher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    sentNotification(c)
                } else {
                    showPermissionDeniedDiatog.value = true
                }

            }

        return permissionLancher
    }

    private fun notificationChanel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("1", "General Chanel", importance)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        channel.description = "Displays general notifications for the application"

        manager.createNotificationChannel(channel)

    }

    private fun notificationChanel2() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("2", "Mini Chanel", importance)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        channel.description = "Displays notifications "

        manager.createNotificationChannel(channel)

    }

    @SuppressLint("MissingPermission")
    private fun sentNotification(c: Context) {

        val link = "https://developer.android.com/compose".toUri()
        val i = Intent(Intent.ACTION_VIEW, link)
        //عند النقر علي الاشعار تتدخل علي اللينك
        val pendingIntent =
            PendingIntent.getActivity(c, 0, i, PendingIntent.FLAG_IMMUTABLE)
        //تحول الصورة لبكسيل
       // val bitmap = BitmapFactory.decodeResource(c.resources, R.drawable.compose_quote)
        // خصائص الاشعار
        val builder = NotificationCompat.Builder(c, "1")
            .setContentTitle("New Notificaton!")
            .setSmallIcon(R.drawable.fitness)
            .setContentText("Smell the rose, you are using compose")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        //.bigLargeIcon(null))
        NotificationManagerCompat.from(c).notify(66, builder)
    }

    @Composable
    fun PermissionDeniedDiaLog(onActionClicked: () -> Unit) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {

                TextButton(onClick = {
                    val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    i.data = Uri.fromParts("package", "com.example.notifcations", null)
                    startActivity(i)
                    onActionClicked()
                })
                /* TextButton(onClick = {
                     val i = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                     i.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                     startActivity(i)
                     onActionClicked()
                 })*/ {
                    Text(text = "Allow")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onActionClicked()
                }) {
                    Text(text = "Cancel")
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_warning_amber_24),
                    contentDescription = "Warning"
                )
            },
            title = {
                Text(text = "We need your permission to send notification")
            },
            text = {
                Text(text = stringResource(id = R.string.alert_content))
            }
        )
    }

}*/