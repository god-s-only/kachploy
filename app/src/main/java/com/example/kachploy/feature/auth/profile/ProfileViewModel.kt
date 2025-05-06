package com.example.kachploy.feature.auth.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kachploy.models.UserInformation
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {
    // Private mutable state flow
    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Nothing)

    // Public immutable state flow
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    // Firebase references
    private val userDocument = Firebase.firestore
    private val currentUser = Firebase.auth.currentUser
    private val storage = Firebase.storage.reference

    /**
     * Saves user information to Firestore with profile picture upload to Firebase Storage
     */
    fun saveUserInformation(
        address: String,
        availability: String,
        phone: String,
        role: String,
        yearsOfExperience: String,
        uri: Uri?
    ) {
        viewModelScope.launch {
            try {
                _profileState.value = ProfileState.Loading

                // Check if user is authenticated
                if (currentUser == null) {
                    _profileState.value = ProfileState.Error("User not authenticated")
                    return@launch
                }

                // Handle profile picture
                val profilePicUrl = if (uri != null) {
                    uploadProfilePicture(uri)
                } else {
                    // Use default or current profile picture URL
                    currentUser.photoUrl?.toString() ?: ""
                }

                // Create user information object
                val userInformation = UserInformation(
                    address = address,
                    availability = availability,
                    email = currentUser.email,
                    phone = phone,
                    profilePic = profilePicUrl,
                    role = role,
                    fullName = currentUser.displayName,
                    yearsOfExperience = yearsOfExperience
                )

                // Save to Firestore
                saveUserToFirestore(userInformation)

            } catch (e: Exception) {
                _profileState.value = ProfileState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    /**
     * Uploads profile picture to Firebase Storage
     * @return Download URL of the uploaded image
     */
    private suspend fun uploadProfilePicture(uri: Uri): String = withContext(Dispatchers.IO) {
        try {
            val storageRef = storage
                .child("profilePics")
                .child("image_${Timestamp.now().nanoseconds}.jpg")

            // Upload file and await completion
            val uploadTask = storageRef.putFile(uri).await()

            // Get download URL
            return@withContext storageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            throw Exception("Failed to upload profile picture: ${e.message}")
        }
    }

    /**
     * Saves user information to Firestore
     */
    private suspend fun saveUserToFirestore(userInformation: UserInformation) = withContext(Dispatchers.IO) {
        try {
            userDocument
                .collection("users")
                .document(currentUser?.uid ?: throw Exception("User ID not available"))
                .set(userInformation)
                .await()

            // Update state on success
            _profileState.value = ProfileState.Success
        } catch (e: Exception) {
            throw Exception("Failed to save user data: ${e.message}")
        }
    }

    /**
     * Get current user information from Firestore
     */
    fun getUserInformation() {
        viewModelScope.launch {
            try {
                _profileState.value = ProfileState.Loading

                if (currentUser == null) {
                    _profileState.value = ProfileState.Error("User not authenticated")
                    return@launch
                }

                val userDoc = userDocument
                    .collection("users")
                    .document(currentUser.uid)
                    .get()
                    .await()

                if (userDoc.exists()) {
                    val user = userDoc.toObject(UserInformation::class.java)
                    _profileState.value = user?.let { ProfileState.UserLoaded(it) }
                        ?: ProfileState.Error("Failed to parse user data")
                } else {
                    _profileState.value = ProfileState.Empty
                }
            } catch (e: Exception) {
                _profileState.value = ProfileState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clean up any resources if needed
    }
}

/**
 * Sealed class representing the different states of the profile
 */
sealed class ProfileState {
    object Nothing : ProfileState()
    object Loading : ProfileState()
    object Success : ProfileState()
    object Empty : ProfileState()
    data class Error(val message: String) : ProfileState()
    data class UserLoaded(val userInformation: UserInformation) : ProfileState()
}