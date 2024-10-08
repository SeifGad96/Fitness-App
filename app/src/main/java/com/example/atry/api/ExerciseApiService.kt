package com.example.atry.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ExerciseApiService {
    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: b9a9e6a395msh93f083e9a88bcadp17f334jsn2cf46ab896f5"
    )
    @GET("exercises/bodyPart/{bodyPart}")
    suspend fun getExercisesByBodyPart(
        @Path("bodyPart") bodyPart: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<Exercise>

    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: b9a9e6a395msh93f083e9a88bcadp17f334jsn2cf46ab896f5"
    )
    @GET("exercises/exercise/{id}")
    suspend fun getExerciseById(
        @Path("id") id: String
    ): Exercise

}
