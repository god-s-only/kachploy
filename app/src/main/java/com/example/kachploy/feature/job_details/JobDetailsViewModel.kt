package com.example.kachploy.feature.job_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kachploy.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class JobDetailsViewModel: ViewModel() {
    private val _jobState = Channel<UiEvent>()
    val jobState = _jobState.receiveAsFlow()

    fun onEvent(event: JobDetailsEvent){
        when(event){
            is JobDetailsEvent.BuyConnects -> {

            }
            is JobDetailsEvent.ApplyForJob -> {

            }
            is JobDetailsEvent.FetchJobError -> {
                sendUIEvent(UiEvent.ShowSnackBar(
                    "Error fetching jobs",
                    "Retry"
                ))
            }
            is JobDetailsEvent.OnFavoriteAdd -> {

            }

        }
    }
    private fun sendUIEvent(event: UiEvent){
        viewModelScope.launch {
            _jobState.send(event)
        }
    }
}