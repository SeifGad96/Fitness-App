package com.example.atry.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ExerciseApiService {
    @GET("exercises/bodyPart/back")
    fun getExercises( ): Call<List<Exercise>>

    @GET("exercises/exercise/{id}")
    fun getExerciseById(@Path("id") id: String): Call<Exercise>

}
