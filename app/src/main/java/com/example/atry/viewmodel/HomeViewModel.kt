package com.example.atry.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atry.api.Exercise
import com.example.atry.api.ExerciseApiService
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.emptyList


class HomeViewModel : ViewModel() {

    private lateinit var exerciseCall: Call<List<Exercise>>
    private var apiService: ExerciseApiService
    private val state = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = state.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    var client = OkHttpClient()

    init {
        client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-rapidapi-host", "exercisedb.p.rapidapi.com")
                    .addHeader(
                        "x-rapidapi-key",
                        "f4011a63d6msh2eaa4bf48996faap1940d1jsn943a10c616f4"
                    )
                    .build()
                chain.proceed(request)
            }.build()

        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://exercisedb.p.rapidapi.com/")
            .client(client)
            .build()
        apiService = retrofit.create(ExerciseApiService::class.java)


        getExercises()
    }

    fun getExercises() {
        exerciseCall = apiService.getExercises()
        exerciseCall.enqueue(object : Callback<List<Exercise>> {
            override fun onResponse(
                call: Call<List<Exercise>>,
                response: Response<List<Exercise>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("HomeViewModel", "Exercises loaded: ${it.size}")
                        state.value = it//.sortedBy { it.name }
                    }
                } else {
                    response.errorBody()?.let { errorBody ->
                        Log.d("HomeViewModel", "Error: ${errorBody.string()}")
                        _errorMessage.value = errorBody.string()
                    } ?: run {
                        Log.d("HomeViewModel", "Error: Unknown error")
                        _errorMessage.value = "Unknown error"
                    }
                }


            }

            override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
                Log.d("HomeViewModel", "API call failed: ${t.message}")
                _errorMessage.value = t.message
                t.printStackTrace()
            }

        })

    }

    fun fetchExerciseById(id: String, callback: (Exercise?) -> Unit) {
        if (id.isNullOrEmpty()) {
            Log.e("HomeViewModel", "ID is null. Cannot fetch exercise.")
            return
        }

        viewModelScope.launch {
            try {
                val response = apiService.getExerciseById(id)
                response.enqueue(object : Callback<Exercise> {
                    override fun onResponse(call: Call<Exercise>, response: Response<Exercise>) {
                        if (response.isSuccessful) {
                            val exercise = response.body()
                            exercise?.let {

                                Log.d("HomeViewModel", "Fetched exercise: ${it.name}")
                                callback(it)
                            }

                        } else {
                            val errorMessage = response.errorBody()?.string() ?: "unknown error"
                            Log.e("HomeViewModel", "Error: $errorMessage")
                            callback(null)
                        }
                    }

                    override fun onFailure(call: Call<Exercise>, t: Throwable) {
                        Log.e("HomeViewModel", "API call failed: ${t.message}")
                        callback(null)
                    }
                })
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error: ${e.message}")
                callback(null)
            }
        }

    }
}

