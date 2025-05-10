package com.example.kachploy.feature.home

import com.example.kachploy.models.JobsModel

sealed class PostHomeState {
    data class Loading(val jobs: List<JobsModel>) : PostHomeState()
    data class Success(val jobs: List<JobsModel>) : PostHomeState()
    data class Error(val message: String) : PostHomeState()
}