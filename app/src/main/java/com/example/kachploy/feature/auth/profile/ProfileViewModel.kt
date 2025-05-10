package com.example.kachploy.feature.auth.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kachploy.SupabaseStorageUtils
import com.example.kachploy.models.UserInformation
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(@ApplicationContext val context: Context): ViewModel() {
    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Nothing)

    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    private val userDocument = Firebase.firestore
    private val currentUser = Firebase.auth.currentUser


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
                val publicUrl = SupabaseStorageUtils(context)

                // Check if user is authenticated
                if (currentUser == null) {
                    _profileState.value = ProfileState.Error
                    return@launch
                }

                // Handle profile picture
                val profilePicUrl = if (uri != null) {
                    Firebase.auth.currentUser?.updateProfile(UserProfileChangeRequest.Builder().setPhotoUri(uri).build())
                } else {
                    currentUser.photoUrl?.toString() ?: ""
                }

                val userInformation = UserInformation(
                    address = address,
                    availability = availability,
                    email = currentUser.email,
                    phone = phone,
                    role = role,
                    fullName = currentUser.displayName,
                    yearsOfExperience = yearsOfExperience
                )

                saveUserToFirestore(userInformation)

            } catch (e: Exception) {
                _profileState.value = ProfileState.Error
            }
        }
    }


    private suspend fun saveUserToFirestore(userInformation: UserInformation) = withContext(Dispatchers.IO) {
        try {
            userDocument
                .collection("users")
                .document(currentUser?.uid ?: throw Exception("User ID not available"))
                .set(userInformation)
                .await()

            _profileState.value = ProfileState.Success
        } catch (e: Exception) {
            throw Exception("Failed to save user data: ${e.message}")
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}


sealed class ProfileState {
    object Nothing : ProfileState()
    object Loading : ProfileState()
    object Success : ProfileState()
    object Error : ProfileState()
}