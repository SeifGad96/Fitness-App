package com.example.atry.explore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create DataStore
val Context.dataStore by preferencesDataStore(name = "plank_challenge_preferences")

object PlankChallengePreferences {
    private val DIFFICULTY_LEVEL_KEY = intPreferencesKey("difficulty_level")
    private val TIME_LIMIT_KEY = intPreferencesKey("time_limit")

    // Save data
    suspend fun saveChallengeData(context: Context, level: Int, time: Int) {
        context.dataStore.edit { preferences ->
            preferences[DIFFICULTY_LEVEL_KEY] = level
            preferences[TIME_LIMIT_KEY] = time
        }
    }

    // Load data
    fun getChallengeData(context: Context): Flow<Pair<Int, Int>> {
        return context.dataStore.data.map { preferences ->
            val level = preferences[DIFFICULTY_LEVEL_KEY] ?: 1 // Default level is 1
            val time = preferences[TIME_LIMIT_KEY] ?: 30 // Default time is 30 seconds
            Pair(level, time)
        }
    }
}

object SquatChallengePreferences {
    private val DIFFICULTY_LEVEL_KEY = intPreferencesKey("squat_difficulty_level")
    private val TIME_LIMIT_KEY = intPreferencesKey("squat_time_limit")
    private val REPS_KEY = intPreferencesKey("squat_reps")

    // Save data for squat challenge
    suspend fun saveChallengeData(context: Context, level: Int, time: Int, reps: Int) {
        context.dataStore.edit { preferences ->
            preferences[DIFFICULTY_LEVEL_KEY] = level
            preferences[TIME_LIMIT_KEY] = time
            preferences[REPS_KEY] = reps
        }
    }

    // Load data for squat challenge
    fun getChallengeData(context: Context): Flow<Triple<Int, Int, Int>> {
        return context.dataStore.data.map { preferences ->
            val level = preferences[DIFFICULTY_LEVEL_KEY] ?: 1 // Default level
            val time = preferences[TIME_LIMIT_KEY] ?: 30 // Default time in seconds
            val reps = preferences[REPS_KEY] ?: 10 // Default reps
            Triple(level, time, reps)
        }
    }
}

object SpiritChallengePreferences {
    private val DIFFICULTY_LEVEL_KEY = intPreferencesKey("spirit_difficulty_level")
    private val TIME_LIMIT_KEY = intPreferencesKey("spirit_time_limit")

    // Save data for spirit challenge
    suspend fun saveChallengeData(context: Context, level: Int, time: Int) {
        context.dataStore.edit { preferences ->
            preferences[DIFFICULTY_LEVEL_KEY] = level
            preferences[TIME_LIMIT_KEY] = time
        }
    }

    // Load data for spirit challenge
    fun getChallengeData(context: Context): Flow<Pair<Int, Int>> {
        return context.dataStore.data.map { preferences ->
            val level = preferences[DIFFICULTY_LEVEL_KEY] ?: 1 // Default level
            val time = preferences[TIME_LIMIT_KEY] ?: 300 // Default time in seconds (5 minutes)
            Pair(level, time)
        }
    }
}

