package com.example.atry.pages

import androidx.compose.runtime.Composable

@Composable
fun ChallengeDetailsScreen(challengeName: String){
    when(challengeName){
        "Plank Challenge" -> PlankChallenge()
        "Squat Challenge" -> SquatChallenge()
        "Spirit Challenge" -> SpiritChallenge()
    }
}