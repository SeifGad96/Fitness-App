package com.example.atry.api

data class Exercise(

    var bodyPart: String,
    var equipment: String,
    var gifUrl: String,
    var id: String,
    var instructions: List<String>,
    var target: String,
    var name: String,
    var secondaryMuscles: List<String>
)
