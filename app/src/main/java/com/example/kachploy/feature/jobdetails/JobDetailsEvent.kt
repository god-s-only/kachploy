package com.example.kachploy.feature.jobdetails

sealed class JobDetailsEvent{
    object FetchJobError: JobDetailsEvent()
    data class OnFavoriteAdd(val jobId: String): JobDetailsEvent()
    object BuyConnects: JobDetailsEvent()
    data class ApplyForJob(val jobId: String): JobDetailsEvent()
}