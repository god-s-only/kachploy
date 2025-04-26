package com.example.kachploy.models

import com.google.firebase.Timestamp

data class UserInformation(
    val address: String = "",
    val availability: String = "",
    val createdAt: Timestamp? = null,
    val email: String = "",
    val fullName: String = "",
    val phone: String = "",
    val profileComplete: Boolean = true,
    val profilePic: String = "",
    val role: String = "",
    val skills: Array<String> = arrayOf(),
    val yearsOfExperience: String = ""
    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserInformation

        if (profileComplete != other.profileComplete) return false
        if (address != other.address) return false
        if (availability != other.availability) return false
        if (createdAt != other.createdAt) return false
        if (email != other.email) return false
        if (fullName != other.fullName) return false
        if (phone != other.phone) return false
        if (profilePic != other.profilePic) return false
        if (role != other.role) return false
        if (!skills.contentEquals(other.skills)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = profileComplete.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + availability.hashCode()
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        result = 31 * result + email.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + profilePic.hashCode()
        result = 31 * result + role.hashCode()
        result = 31 * result + skills.contentHashCode()
        return result
    }
}
