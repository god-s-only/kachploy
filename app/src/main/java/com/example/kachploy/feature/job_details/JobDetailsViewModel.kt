package com.example.kachploy.feature.job_details

import androidx.lifecycle.ViewModel
import com.example.kachploy.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class JobDetailsViewModel: ViewModel() {
    private val _jobState = Channel<UiEvent>()
    val jobState = _jobState.receiveAsFlow()

    fun onEvent(event: JobDetailsEvent){
        when(event){
            is JobDetailsEvent.BuyConnects -> {

            }
            is JobDetailsEvent.ApplyForJob -> {

            }
            is JobDetailsEvent.FetchJobLoading -> {

            }
            is JobDetailsEvent.FetchJobSuccess -> {

            }
            is JobDetailsEvent.FetchJobError -> {

            }
            is JobDetailsEvent.OnFavoriteAdd -> {

            }

        }
    }
}