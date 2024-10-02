package com.example.atry.calculatorcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun GenderBox(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    color: Color,
) {
    Box(
        modifier = Modifier.height(180.dp).width(180.dp).clickable { onClick.invoke() }.background(color = Color(16,20,39)),
        contentAlignment = Alignment.Center,

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween) {
            Icon(imageVector = icon, tint = color,
                modifier = Modifier.size(60.dp),contentDescription = "$text Icon")
            Text(text = text, style = MaterialTheme.typography.bodyLarge, color = color)
        }
    }
}