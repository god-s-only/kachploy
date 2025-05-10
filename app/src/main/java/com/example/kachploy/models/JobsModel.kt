package com.example.kachploy.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class JobsModel(
    var jobId: String = "",
    var applicationDeadline: String = "",
    var createdAt: String = "",
    var description: String = "",
    var expectedEndDate: String = "",
    var jobStartDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
    var jobType: String = "",
    var location: String = "",
    var negotiable: String = "",
    var postedBy: String = "",
    var price: String = "",
    var proposals: Int? = null,
    var skillsRequired: List<String> = emptyList(),
    var status: String = "",
    var title: String = ""
    )
