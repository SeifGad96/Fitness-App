package com.example.atry.explore

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlankChallenge() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    val savedData =
        PlankChallengePreferences.getChallengeData(context).collectAsState(initial = Pair(1, 30))


    var difficultyLevel by remember { mutableStateOf(savedData.value.first) }
    var timeLimit by remember { mutableStateOf(savedData.value.second) }
    var challengeCompleted by remember { mutableStateOf(false) }
    var timerRunning by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(timeLimit) }

    LaunchedEffect(savedData.value) {
        difficultyLevel = savedData.value.first
        timeLimit = savedData.value.second
        timeLeft = timeLimit
    }


    var showAlertDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Plank Challenge",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Text(
            text = "Instructions: \n\n" +
                    "1. Get into a push-up position with your elbows bent at 90 degrees and rest your weight on your forearms.\n" +
                    "2. Keep your body in a straight line from head to heels, making sure your core is engaged.\n" +
                    "3. Hold this position without letting your hips drop or rise.\n",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )


        Text(
            text = "Challenge Level: $difficultyLevel",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "Challenge: Hold a plank position for $timeLimit seconds",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 7.dp, bottom = 20.dp)
        )

        Text(
            text = "Time Left: $timeLeft seconds",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        CircularProgressIndicator(
            progress = timeLeft.toFloat() / timeLimit,
            modifier = Modifier.size(120.dp),
            color = MaterialTheme.colors.primary,
            strokeWidth = 8.dp
        )
        Button(onClick = {
            Toast.makeText(context, "Challenge Started!", Toast.LENGTH_SHORT).show()
            if (!timerRunning) {
                timerRunning = true
                timeLeft = timeLimit


                scope.launch {
                    while (timeLeft > 0) {
                        delay(1000)
                        timeLeft--
                    }

                    showAlertDialog = true
                    timerRunning = false
                }
            }
        }) {
            Text("Start Challenge")
        }


        ChallengeCompletionDialog(
            context = context,
            showDialog = showAlertDialog,
            onDismiss = {
                showAlertDialog = false

                timeLeft = timeLimit
                timerRunning = false
            },
            onConfirm = { level ->
                challengeCompleted = true

                difficultyLevel++
                timeLimit += 10


                scope.launch {
                    PlankChallengePreferences.saveChallengeData(context, difficultyLevel, timeLimit)
                }

                Toast.makeText(context, "Congrats! You completed Level $level!", Toast.LENGTH_SHORT)
                    .show()
            },
            level = difficultyLevel
        )
    }
}

@Composable
fun SpiritChallenge() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val savedData =
        SpiritChallengePreferences.getChallengeData(context).collectAsState(initial = Pair(1, 300))


    var difficultyLevel by remember { mutableStateOf(savedData.value.first) }
    var timeLimit by remember { mutableStateOf(savedData.value.second) }
    var challengeCompleted by remember { mutableStateOf(false) }
    var timerRunning by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(timeLimit) }


    LaunchedEffect(savedData.value) {
        difficultyLevel = savedData.value.first
        timeLimit = savedData.value.second
        timeLeft = timeLimit
    }

    var showAlertDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Spirit Challenge",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Instructions: \n\n" +
                    "1. Find a safe area to run.\n" +
                    "2. Start running at a comfortable pace.\n",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Challenge Level: $difficultyLevel",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "Challenge: Run for $timeLimit seconds",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 7.dp, bottom = 20.dp)
        )

        Text(
            text = "Time Left: $timeLeft seconds",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        CircularProgressIndicator(
            progress = timeLeft.toFloat() / timeLimit,
            modifier = Modifier.size(120.dp),
            color = MaterialTheme.colors.primary,
            strokeWidth = 8.dp
        )

        Button(onClick = {
            Toast.makeText(context, "Challenge Started!", Toast.LENGTH_SHORT).show()
            if (!timerRunning) {
                timerRunning = true
                timeLeft = timeLimit


                scope.launch {
                    while (timeLeft > 0) {
                        delay(1000)
                        timeLeft--
                    }

                    showAlertDialog = true
                    timerRunning = false
                }
            }
        }) {
            Text("Start Challenge")
        }


        ChallengeCompletionDialog(
            context = context,
            showDialog = showAlertDialog,
            onDismiss = {
                showAlertDialog = false

                timeLeft = timeLimit
                timerRunning = false
            },
            onConfirm = { level ->
                challengeCompleted = true
                difficultyLevel++
                timeLimit += 60

                scope.launch {
                    SpiritChallengePreferences.saveChallengeData(
                        context,
                        difficultyLevel,
                        timeLimit
                    )
                }
                showAlertDialog = false

                Toast.makeText(context, "Congrats! You completed Level $level!", Toast.LENGTH_SHORT)
                    .show()
            },
            level = difficultyLevel
        )
    }
}

@Composable
fun ChallengeCompletionDialog(
    context: Context,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    level: Int
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Challenge Completed") },
            text = { Text("Did you complete the challenge on time?") },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm(level)
                        onDismiss()
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Keep going, you’ve got this!",
                            Toast.LENGTH_SHORT
                        ).show()
                        onDismiss()
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
fun SquatChallenge() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    val savedData = SquatChallengePreferences.getChallengeData(context)
        .collectAsState(initial = Triple(1, 30, 10))


    var difficultyLevel by remember { mutableStateOf(savedData.value.first) }
    var timeLimit by remember { mutableStateOf(savedData.value.second) }
    var reps by remember { mutableStateOf(savedData.value.third) }
    var challengeCompleted by remember { mutableStateOf(false) }
    var timerRunning by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(timeLimit) }


    LaunchedEffect(savedData.value) {
        difficultyLevel = savedData.value.first
        timeLimit = savedData.value.second
        reps = savedData.value.third
        timeLeft = timeLimit
    }


    var showAlertDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Squat Challenge",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Text(
            text = "Instructions: \n\n" +
                    "1. Stand with your feet shoulder-width apart and your arms extended straight forward.\n" +
                    "2. Slowly bend your knees and lower your body into a squat position, keeping your back straight and core engaged.\n" +
                    "3. Push through your heels to return to the starting position.\n",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )


        Text(
            text = "Challenge Level: $difficultyLevel",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "Challenge: Complete $reps squats in $timeLimit seconds",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 7.dp, bottom = 20.dp)
        )

        // Timer Display
        Text(
            text = "Time Left: $timeLeft seconds",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Timer visualization
        CircularProgressIndicator(
            progress = timeLeft.toFloat() / timeLimit,
            modifier = Modifier.size(120.dp),
            color = MaterialTheme.colors.primary,
            strokeWidth = 8.dp
        )

        // Button to start the challenge (and timer)
        Button(onClick = {
            Toast.makeText(context, "Challenge Started!", Toast.LENGTH_SHORT).show()

            if (!timerRunning) {
                timerRunning = true
                timeLeft = timeLimit // Reset time left to the time limit

                // Start the timer
                scope.launch {
                    while (timeLeft > 0) {
                        delay(1000) // Delay for 1 second
                        timeLeft-- // Decrement the time left
                    }

                    // Timer finished, show alert dialog
                    showAlertDialog = true
                    timerRunning = false
                }
            }
        }) {
            Text("Start Challenge")
        }

        // AlertDialog for challenge completion
        ChallengeCompletionDialog(
            context = context,
            showDialog = showAlertDialog,
            onDismiss = {
                showAlertDialog = false // Reset time left when dialog is dismissed
                timeLeft = timeLimit
                timerRunning = false
            },
            onConfirm = { level ->
                challengeCompleted = true
                // Increase the difficulty level, time limit, and reps
                difficultyLevel++
                timeLimit += 10
                reps += 5 // Increase reps by 5 for each level

                // Save the updated data to DataStore
                scope.launch {
                    SquatChallengePreferences.saveChallengeData(
                        context,
                        difficultyLevel,
                        timeLimit,
                        reps
                    )
                }

                Toast.makeText(context, "Congrats! You completed Level $level!", Toast.LENGTH_SHORT)
                    .show()
            },
            level = difficultyLevel
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun showPlankChallenge() {
    PlankChallenge()

}
