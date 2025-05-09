package com.example.kachploy.feature.home

import com.example.kachploy.models.JobsModel

sealed class PostHomeState {
    object Nothing : PostHomeState()
    object Loading : PostHomeState()
    object Success : PostHomeState()
    object Empty : PostHomeState()
    data class Error(val message: String) : PostHomeState()
    data class UserLoaded(val userInformation: JobsModel) : PostHomeState()
}