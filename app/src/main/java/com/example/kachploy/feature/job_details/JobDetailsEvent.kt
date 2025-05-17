package com.example.kachploy.feature.job_details

import com.example.kachploy.models.JobsModel

sealed class JobDetailsEvent{
    object FetchJobError: JobDetailsEvent()
    data class OnFavoriteAdd(val jobId: String): JobDetailsEvent()
    object BuyConnects: JobDetailsEvent()
    data class ApplyForJob(val jobId: String): JobDetailsEvent()
}