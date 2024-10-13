package com.example.atry.Setting

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.atry.MainActivity
import java.util.concurrent.TimeUnit

@Composable
fun Notification() {
    var showPermissionDeniedDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Manage permission request
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // If granted, start notifications
            startRepeatingNotifications(context)
        } else {
            showPermissionDeniedDialog = true // Show permission denied dialog
        }
    }

    // Launch permission request
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    if (showPermissionDeniedDialog) {
        PermissionDeniedDiaLog(
            onActionClicked = { showPermissionDeniedDialog = false },
            context = context
        )
    }
}


 fun startRepeatingNotifications(context: Context) {



    // إعداد WorkRequest لإرسال الإشعارات كل 15 دقيقة (الحد الأدنى)
    val workRequest: WorkRequest =
        PeriodicWorkRequestBuilder<NotificationWorker>(2, TimeUnit.HOURS)
            .build()
    WorkManager.getInstance(context).enqueue(workRequest)
}
fun stopRepeatingNotifications(context: Context) {
    Log.d("Notification", "Cancelling notifications...")
    WorkManager.getInstance(context).cancelUniqueWork("notification_work")
}
@SuppressLint("MissingPermission")
private fun sentNotification(c: Context) {

    val intent = Intent(c, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(c, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(c, "1")
        .setContentTitle("New Notification!")
        .setSmallIcon(R.drawable.ic_dialog_info)
        .setContentText("We remind you to drink water")
        .setAutoCancel(true)
        .setContentIntent(pendingIntent) // تعيين النية على الإشعار
        .build()

    NotificationManagerCompat.from(c).notify(66, builder)
}


class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        createNotificationChannel(applicationContext)
        sentNotification(applicationContext)
        return Result.success()
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", "General Channel", importance).apply {
                description = "Displays general notifications for the application"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

@Composable
fun PermissionDeniedDiaLog(onActionClicked: () -> Unit, context: Context) {
    AlertDialog(
        onDismissRequest = { /* يمكن تركها فارغة */ },
        confirmButton = {
            TextButton(onClick = {
                // Open app notification settings
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", context.packageName, null)
                context.startActivity(intent)
                onActionClicked() // Close the dialog
            }) {
                Text(text = "Allow")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onActionClicked() // Close the dialog
            }) {
                Text(text = "Cancel")
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_dialog_alert),
                contentDescription = "Warning"
            )
        },
        title = {
            Text(text = "We need your permission to send notifications")
        },
        text = {
            Text(text = "Please grant notification permission to continue.")
        }
    )
}



