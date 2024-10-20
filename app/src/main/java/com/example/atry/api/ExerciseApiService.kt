package com.example.atry.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ExerciseApiService {
    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: 093bce7cd8mshe54f72b3506a132p1146d5jsn53a1482b36ec"
    )
    @GET("exercises/bodyPart/{bodyPart}")
    suspend fun getExercisesByBodyPart(
        @Path("bodyPart") bodyPart: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<Exercise>

    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: 093bce7cd8mshe54f72b3506a132p1146d5jsn53a1482b36ec"
    )
    @GET("exercises/exercise/{id}")
    suspend fun getExerciseById(
        @Path("id") id: String
    ): Exercise
    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: 093bce7cd8mshe54f72b3506a132p1146d5jsn53a1482b36ec"
    )
    @GET("exercises")
    suspend fun getAllExercises(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<Exercise>

}
