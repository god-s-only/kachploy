package com.example.kachploy.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kachploy.models.JobsModel
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

    private var _homeState = MutableStateFlow<PostHomeState>(PostHomeState.Loading(emptyList()))
    var homeState = _homeState.asStateFlow()

    init {
        getUserInformation()
    }

    fun getUserInformation() {
        val list = mutableListOf<JobsModel>()
        viewModelScope.launch {
            try {
                Firebase.firestore.collection("jobs").get()
                    .addOnSuccessListener { snapshot ->
                        snapshot.mapNotNull {
                            val jobsModel = it.toObject(JobsModel::class.java)
                            list.add(jobsModel)
                        }
                        _homeState.value = PostHomeState.Success(list)
                    }
            } catch (e: Exception) {
                _homeState.value = PostHomeState.Error("Error loading jobs")
            }
        }
    }

}