package com.example.kachploy.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kachploy.models.UserInformation
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    private var _homeState = MutableStateFlow<PostHomeState>(PostHomeState.Empty)
    var homeState = _homeState.asStateFlow()

    fun getUserInformation() {
        viewModelScope.launch {
            try {
                _homeState.value = PostHomeState.Loading

                if (Firebase.auth.currentUser == null) {
                    _homeState.value = PostHomeState.Error("User not authenticated")
                    return@launch
                }
                val userDoc = Firebase.firestore
                    .collection("users")
                    .document(Firebase.auth.currentUser!!.uid)
                    .get()
                    .await()

                if (userDoc.exists()) {
                    val user = userDoc.toObject(UserInformation::class.java)
                    _homeState.value = user?.let { PostHomeState.UserLoaded(it) }
                        ?: PostHomeState.Error("Failed to parse user data")
                } else {
                    _homeState.value = PostHomeState.Empty
                }
            } catch (e: Exception) {
                _homeState.value = PostHomeState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

}