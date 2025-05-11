package com.example.kachploy.feature.job_details

import com.example.kachploy.models.JobsModel

sealed class JobDetailsEvent{
    data class FetchJobSuccess(val jobsModel: JobsModel): JobDetailsEvent()
    data class FetchJobError(val jobsModel: JobsModel): JobDetailsEvent()
    data class FetchJobLoading(val jobsModel: JobsModel): JobDetailsEvent()
    data class OnFavoriteAdd(val jobId: String): JobDetailsEvent()
    object BuyConnects: JobDetailsEvent()
    data class ApplyForJob(val jobId: String): JobDetailsEvent()
}