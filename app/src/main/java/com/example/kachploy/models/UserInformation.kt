package com.example.kachploy.models

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class UserInformation(
    val address: String = "",
    val availability: String = "",
    val createdAt: String? = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
    val email: String? = "",
    val fullName: String? = "",
    val phone: String = "",
    val profileComplete: Boolean = true,
    val role: String = "",
    val yearsOfExperience: String = ""
    )