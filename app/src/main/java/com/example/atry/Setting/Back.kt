import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun Back_Handler() {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // التعامل مع زر الرجوع
    BackHandler {
        showDialog = true
    }

    // عرض الحوار عند الضغط على زر الرجوع
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // لا تغلق الحوار عند النقر خارج الحوار
            },
            title = {
                Text(text = "Confirm exit")
            },
            text = {
                Text("Are you sure you want to exit the app?")
            },
            confirmButton = {
                Button(onClick = {
                    // إغلاق التطبيق
                    (context as? Activity)?.finish()
                }) {
                    Text("Ok")
                }
            },
            dismissButton = {
                Button(onClick = {
                    // إلغاء الحوار
                    showDialog = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }


}
