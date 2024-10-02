package com.example.atry.calculatorcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.atry.pages.RoundIconButton

@Composable
fun HeightAndAge(
    text: String,
    value: MutableState<Int>,
) {
    Row(modifier = Modifier.padding(5.dp)) {
        Box(modifier = Modifier
            .height(200.dp)
            .width(178.dp)
            .background(Color(16,20,39))) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .fillMaxHeight()) {
                Text(text = "$text", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(0.5f), fontWeight = FontWeight.Bold)
                Text(text = "${value.value}", color = Color.White,style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(5.dp))
                Row() {
                    RoundIconButton(imageVector = Icons.Default.Remove, onClick = {
                        if (value.value > 1) value.value--
                    })
                    Spacer(modifier = Modifier.width(5.dp))
                    RoundIconButton(imageVector = Icons.Default.Add, onClick = { value.value++})
                }
            }
        }
    }
}