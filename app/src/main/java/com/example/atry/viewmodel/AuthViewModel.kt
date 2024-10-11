package com.example.atry.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class AuthViewModel : ViewModel() {


    private var username by mutableStateOf("")

    fun updateUsername(newUsername: String) {
        username = newUsername
    }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: LiveData<FirebaseUser?> get() = _firebaseUser

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        _firebaseUser.postValue(firebaseAuth.currentUser)
    }

    val _toastMessage = MutableLiveData<String?>()

    init {
        auth.addAuthStateListener(authStateListener)
    }

    init {
        checkUserLoggedInStatus()
    }

    fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("AuthViewModel", "Login successful")
                if (auth.currentUser?.isEmailVerified == true) {
                    _firebaseUser.postValue(auth.currentUser)
                    _toastMessage.postValue("Login successful")
                } else {
                    Log.d("AuthViewModel", "Email not verified")
                    _toastMessage.postValue("Email is not verified")
                }
            } else {
                Log.d("AuthViewModel", "Login failed: ${task.exception?.message}")
                _toastMessage.postValue("Login failed: ${task.exception?.message}")
            }
        }
    }

    fun signUp(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                user?.let {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()

                    it.updateProfile(profileUpdates).addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            _toastMessage.postValue("Sign up successful! Verification email sent.")
                            user.sendEmailVerification()
                        } else {
                            _toastMessage.postValue("Failed to update profile: ${updateTask.exception?.message}")
                        }
                    }
                }
            } else {
                _toastMessage.postValue("Sign up failed: ${task.exception?.message}")
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _firebaseUser.postValue(null) // Set to null to indicate the user is signed out
    }

    fun sendPasswordResetEmail(email: String) {
        if (email.isBlank()) {
            _toastMessage.postValue("Email is required")
        } else {
            auth.sendPasswordResetEmail(email).addOnSuccessListener {
                _toastMessage.postValue("Password reset email sent")
            }.addOnFailureListener {
                _toastMessage.postValue(it.message ?: "Error sending password reset email")
            }
        }
    }

    fun checkUserLoggedInStatus() {
        _firebaseUser.postValue(auth.currentUser) // If the user is already logged in, firebaseUser will have a value
    }

    override fun onCleared() {
        super.onCleared()
        auth.removeAuthStateListener(authStateListener)
    }

    fun saveUserData(weight: Int, height: Float, age: Int, gender: Int, bmi: String) {
        val user = auth.currentUser
        user?.let {
            val userData = hashMapOf(
                "weight" to weight,
                "height" to height,
                "age" to age,
                "bmi" to bmi,
                "gender" to gender
            )
            firestore.collection("users").document(user.uid)
                .set(userData, SetOptions.merge()) // Merge the data with the existing user document
                .addOnSuccessListener {
                    _toastMessage.postValue("User data saved successfully")
                }
                .addOnFailureListener { e ->
                    _toastMessage.postValue("Error saving user data: ${e.message}")
                }
        } ?: run {
            _toastMessage.postValue("User not logged in")
        }
    }
    fun clearToastMessage() {
        _toastMessage.postValue(null) // Clears the current toast message
    }
}
