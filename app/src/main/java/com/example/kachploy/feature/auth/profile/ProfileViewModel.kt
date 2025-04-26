package com.example.kachploy.feature.auth.profile

import androidx.lifecycle.ViewModel
import com.example.kachploy.models.UserInformation
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {
    private var _profileState = MutableStateFlow<ProfileState>(ProfileState.Nothing)
    var profileState = _profileState.asStateFlow()
    val userDocument = Firebase.firestore
    val currentUser = Firebase.auth

    fun saveUserInformation(address: String, availability: String, phone: String, profilePic: String, role: String, yearsOfExperience: String){
        _profileState.value = ProfileState.Loading
        val userInformation = UserInformation(
            address,
            availability,
            Timestamp.now(),
            currentUser.currentUser?.email,
            currentUser.currentUser?.displayName,
            phone,
            true,
            profilePic,
            role,
            yearsOfExperience
        )
        currentUser.currentUser?.uid?.let {
            userDocument.collection("users").document(it).set(userInformation).addOnSuccessListener {
                _profileState.value = ProfileState.Success
            }.addOnFailureListener {
                _profileState.value = ProfileState.Error
            }
        }
    }

}
sealed class ProfileState{
    object Nothing: ProfileState()
    object Loading: ProfileState()
    object Success: ProfileState()
    object Error: ProfileState()
}