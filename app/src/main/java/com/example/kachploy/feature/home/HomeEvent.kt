package com.example.kachploy.feature.home

sealed class HomeEvent {
    data class GotoJobDetails(val jobId: String): HomeEvent()
}