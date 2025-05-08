package com.example.kachploy.models

import com.google.firebase.Timestamp

data class UserInformation(
    val address: String = "",
    val availability: String = "",
    val createdAt: Timestamp? = null,
    val email: String? = "",
    val fullName: String? = "",
    val phone: String = "",
    val profileComplete: Boolean = true,
    val profilePic: String? = null,
    val role: String = "",
    val yearsOfExperience: String = ""
    )