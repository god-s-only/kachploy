package com.example.kachploy.feature.job_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kachploy.models.JobsModel
import com.example.kachploy.util.UiEvent
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class JobDetailsViewModel: ViewModel() {
    var jobDetail by mutableStateOf<JobsModel?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    private val _jobState = Channel<UiEvent>()
    val jobState = _jobState.receiveAsFlow()

    fun getJobDetail(jobId: String) {
        isLoading = true
        Firebase.firestore.collection("jobs").document(jobId).get()
            .addOnSuccessListener { data ->
                isLoading = false
                data.toObject(JobsModel::class.java)?.let {
                    jobDetail = it
                }
            }.addOnFailureListener {
                isLoading = false
                sendUIEvent(UiEvent.ShowSnackBar("Error fetching job", "Retry"))
            }
    }

    private fun sendUIEvent(event: UiEvent) {
        viewModelScope.launch {
            _jobState.send(event)
        }
    }
    fun onEvent(event: JobDetailsEvent) {
        when (event) {
            is JobDetailsEvent.BuyConnects -> {

            }

            is JobDetailsEvent.ApplyForJob -> {
            }

            is JobDetailsEvent.OnFavoriteAdd -> {
            }

            is JobDetailsEvent.FetchJobError -> {
                sendUIEvent(
                    UiEvent.ShowSnackBar(
                        message = "Error fetching job",
                        action = "Retry"
                    )
                )
            }

        }
    }

}